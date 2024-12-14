package itmo.spacemarineservice.domain.services;

import itmo.spacemarineservice.controllers.request.SpaceMarineCreateRequest;
import itmo.spacemarineservice.controllers.request.SpaceMarineUpdateRequest;
import itmo.spacemarineservice.domain.dto.*;
import itmo.spacemarineservice.domain.exceptions.SpaceMarineNotFoundException;
import itmo.spacemarineservice.domain.repositories.ChapterRepository;
import itmo.spacemarineservice.domain.repositories.SpaceMarineRepository;
import itmo.spacemarineservice.domain.repositories.entities.ChapterEntity;
import itmo.spacemarineservice.domain.repositories.entities.SpaceMarineEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Service
public class SpaceMarineService {
    @Autowired
    private SpaceMarineRepository spaceMarineRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    public PaginatedSpaceMarines findSpaceMarines(SpaceMarineQueryParams queryParams) {
        var spaceMarines = enrichSpaceMarines(spaceMarineRepository.findAll());
        var filteredMarines = new ArrayList<>(spaceMarines.stream().filter((el) ->
                queryParams.filterParams().isEmpty() ||
                        queryParams.filterParams().stream().filter((f) -> compare(el, f)).toList().size() == queryParams.filterParams().size()
        ).toList());
        queryParams.sortParams().forEach((el) ->
                sort(filteredMarines, el)
        );

        var spaceMarinesSize = filteredMarines.size();
        var totalPage = 1 + (spaceMarinesSize - 1) / queryParams.pageSize();
        var firstItemIndex = (queryParams.page() - 1) * queryParams.pageSize();
        var lastItemIndex = queryParams.page() * queryParams.pageSize();

        var items = totalPage < queryParams.page() ?
                new ArrayList<SpaceMarine>() :
                (totalPage == queryParams.page() ? filteredMarines.subList(firstItemIndex, spaceMarinesSize) : filteredMarines.subList(firstItemIndex, lastItemIndex));
        return new PaginatedSpaceMarines(
                queryParams.page(),
                totalPage,
                items.size(),
                items
        );
    }

    public SpaceMarine findSpaceMarine(Integer id) {
        var spaceMarineEntityOptional = spaceMarineRepository.findById(id);
        if (spaceMarineEntityOptional.isPresent()) {
            var spaceMarineEntity = spaceMarineEntityOptional.get();
            var chapterEntity = chapterRepository.findById(spaceMarineEntity.getChapter()).get();
            return new SpaceMarine(
                    spaceMarineEntity.getId(),
                    spaceMarineEntity.getName(),
                    new Coordinates(spaceMarineEntity.getX(), spaceMarineEntity.getY()),
                    spaceMarineEntity.getCreationDate(),
                    spaceMarineEntity.getHealth(),
                    spaceMarineEntity.getCategory(),
                    spaceMarineEntity.getWeaponType(),
                    spaceMarineEntity.getMeleeWeapon(),
                    new Chapter(chapterEntity.getName(), chapterEntity.getMarinesCount())
            );
        }
        return null;
    }

    @Transactional
    public Integer createSpaceMarine(SpaceMarineCreateRequest spaceMarineCreateRequest) {
        var result = spaceMarineRepository.save(
                new SpaceMarineEntity(
                        null,
                        spaceMarineCreateRequest.name(),
                        spaceMarineCreateRequest.coordinates().x(),
                        spaceMarineCreateRequest.coordinates().y(),
                        ZonedDateTime.now(),
                        spaceMarineCreateRequest.health(),
                        spaceMarineCreateRequest.category(),
                        spaceMarineCreateRequest.weaponType(),
                        spaceMarineCreateRequest.meleeWeapon(),
                        spaceMarineCreateRequest.chapterName()
                )
        );

        createOrUpdateChapter(result);
        return result.getId();
    }

    public Double calculateSpaceMarineHealthAvg() {
        var marines = spaceMarineRepository.findAll();

        return marines.stream().map(SpaceMarineEntity::getHealth).reduce(Double::sum).orElse(0.0) / marines.size();
    }

    public List<GroupingByNameSpaceMarine> groupByName() {
        var marines = enrichSpaceMarines(spaceMarineRepository.findAll());
        var groups = marines.stream().collect(groupingBy(SpaceMarine::name));

        return groups.keySet().stream().map(key -> new GroupingByNameSpaceMarine(key, groups.get(key))).toList();
    }

    @Transactional
    public SpaceMarine updateSpaceMarine(Integer id, SpaceMarineUpdateRequest spaceMarineUpdateRequest) {
        var spaceMarineEntityOptional = spaceMarineRepository.findById(id);
        if (spaceMarineEntityOptional.isEmpty())
            throw new SpaceMarineNotFoundException("Десантник с id=" + id + " не найден");
        var spaceMarineEntity = spaceMarineEntityOptional.get();

        if (!spaceMarineEntity.getName().equals(spaceMarineUpdateRequest.name()))
            spaceMarineEntity.setName(spaceMarineUpdateRequest.name());
        if (spaceMarineUpdateRequest.coordinates() != null && !spaceMarineUpdateRequest.coordinates().x().equals(spaceMarineEntity.getX())) {
            spaceMarineEntity.setX(spaceMarineUpdateRequest.coordinates().x());
        }
        if (spaceMarineUpdateRequest.coordinates() != null && !spaceMarineUpdateRequest.coordinates().y().equals(spaceMarineEntity.getY())) {
            spaceMarineEntity.setY(spaceMarineUpdateRequest.coordinates().y());
        }
        if (!spaceMarineUpdateRequest.health().equals(spaceMarineEntity.getHealth())) {
            spaceMarineEntity.setHealth(spaceMarineUpdateRequest.health());
        }
        if (spaceMarineUpdateRequest.category() != spaceMarineEntity.getCategory()) {
            spaceMarineEntity.setCategory(spaceMarineUpdateRequest.category());
        }
        if (spaceMarineUpdateRequest.weaponType() != spaceMarineEntity.getWeaponType()) {
            spaceMarineEntity.setWeaponType(spaceMarineUpdateRequest.weaponType());
        }
        if (spaceMarineUpdateRequest.meleeWeapon() != spaceMarineEntity.getMeleeWeapon()) {
            spaceMarineEntity.setMeleeWeapon(spaceMarineUpdateRequest.meleeWeapon());
        }
        if (!spaceMarineUpdateRequest.chapterName().equals(spaceMarineEntity.getChapter())) {
            spaceMarineEntity.setChapter(spaceMarineUpdateRequest.chapterName());
        }

        spaceMarineRepository.save(spaceMarineEntity);

        var chapterEntity = createOrUpdateChapter(spaceMarineEntity);

        return new SpaceMarine(
                spaceMarineEntity.getId(),
                spaceMarineEntity.getName(),
                new Coordinates(spaceMarineEntity.getX(), spaceMarineEntity.getY()),
                spaceMarineEntity.getCreationDate(),
                spaceMarineEntity.getHealth(),
                spaceMarineEntity.getCategory(),
                spaceMarineEntity.getWeaponType(),
                spaceMarineEntity.getMeleeWeapon(),
                new Chapter(chapterEntity.getName(), chapterEntity.getMarinesCount())
        );
    }

    private ChapterEntity createOrUpdateChapter(SpaceMarineEntity spaceMarineEntity) {
        var chapterOptional = chapterRepository.findByNameForUpdate(spaceMarineEntity.getChapter());
        var chapter = chapterOptional.orElseGet(() -> new ChapterEntity(
                spaceMarineEntity.getChapter(),
                0
        ));
        chapter.setMarinesCount(chapter.getMarinesCount() + 1);
        chapterRepository.save(chapter);

        return chapter;
    }

    @Transactional
    public void deleteSpaceMarine(Integer id) {
        var spaceMarineEntityOptional = spaceMarineRepository.findById(id);

        if (spaceMarineEntityOptional.isEmpty()) return;
        var spaceMarineEntity = spaceMarineEntityOptional.get();

        spaceMarineRepository.deleteById(spaceMarineEntity.getId());

        var chapterOptional = chapterRepository.findByNameForUpdate(spaceMarineEntity.getChapter());
        var chapter = chapterOptional.get();
        chapter.setMarinesCount(chapter.getMarinesCount() - 1);
        chapterRepository.save(chapter);
    }

    private List<SpaceMarine> enrichSpaceMarines(List<SpaceMarineEntity> entities) {
        return entities.stream().map((spaceMarineEntity) -> {
            var chapterEntity = chapterRepository.findById(spaceMarineEntity.getChapter()).get();
            return new SpaceMarine(
                    spaceMarineEntity.getId(),
                    spaceMarineEntity.getName(),
                    new Coordinates(spaceMarineEntity.getX(), spaceMarineEntity.getY()),
                    spaceMarineEntity.getCreationDate(),
                    spaceMarineEntity.getHealth(),
                    spaceMarineEntity.getCategory(),
                    spaceMarineEntity.getWeaponType(),
                    spaceMarineEntity.getMeleeWeapon(),
                    new Chapter(chapterEntity.getName(), chapterEntity.getMarinesCount())
            );
        }).sorted(Comparator.comparing(SpaceMarine::creationDate).reversed()).toList();
    }

    private Boolean compare(SpaceMarine spaceMarine, SpaceMarineFilterParam filterParam) {
        var compareToResult = switch (filterParam.field()) {
            case ID -> ((Integer) filterParam.extraValue()).compareTo(spaceMarine.id());
            case NAME -> ((String) filterParam.extraValue()).compareTo(spaceMarine.name());
            case COORDINATES_X -> ((Float) filterParam.extraValue()).compareTo(spaceMarine.coordinates().x());
            case COORDINATES_Y -> ((Long) filterParam.extraValue()).compareTo(spaceMarine.coordinates().y());
            case CREATION_DATE -> ((ZonedDateTime) filterParam.extraValue()).compareTo(spaceMarine.creationDate());
            case HEALTH -> ((Double) filterParam.extraValue()).compareTo(spaceMarine.health());
            case CATEGORY ->
                    ((AstartesCategory) filterParam.extraValue()).name().compareTo(spaceMarine.category().name());
            case WEAPON_TYPE -> ((Weapon) filterParam.extraValue()).name().compareTo(spaceMarine.weaponType().name());
            case MELEE_WEAPON ->
                    ((MeleeWeapon) filterParam.extraValue()).name().compareTo(spaceMarine.meleeWeapon().name());
            case CHAPTER_NAME -> ((String) filterParam.extraValue()).compareTo(spaceMarine.chapter().name());
            case CHAPTER_MARINES_COUNT ->
                    ((Integer) filterParam.extraValue()).compareTo(spaceMarine.chapter().marinesCount());
        };

        return switch (filterParam.type()) {
            case GREATER -> compareToResult > 0;
            case LESS -> compareToResult < 0;
            case EQUAL -> compareToResult == 0;
            case GREATER_EQUAL -> compareToResult >= 0;
            case LESS_EQUAL -> compareToResult <= 0;
        };
    }

    private void sort(ArrayList<SpaceMarine> spaceMarines, SpaceMarineSortParam sortParam) {
        spaceMarines.sort((el1, el2) -> {
            var compareToResult = switch (sortParam.field()) {
                case ID -> Integer.compare(el1.id(), el2.id());
                case NAME -> el1.name().compareTo(el2.name());
                case COORDINATES_X -> Float.compare(el1.coordinates().x(), el2.coordinates().x());
                case COORDINATES_Y -> Long.compare(el1.coordinates().y(), el2.coordinates().y());
                case CREATION_DATE -> el1.creationDate().compareTo(el2.creationDate());
                case HEALTH -> Double.compare(el1.health(), el2.health());
                case CATEGORY -> el1.category().name().compareTo(el2.category().name());
                case WEAPON_TYPE -> el1.weaponType().name().compareTo(el2.weaponType().name());
                case MELEE_WEAPON -> el1.meleeWeapon().name().compareTo(el2.meleeWeapon().name());
                case CHAPTER_NAME -> el1.chapter().name().compareTo(el2.chapter().name());
                case CHAPTER_MARINES_COUNT ->
                        Integer.compare(el1.chapter().marinesCount(), el2.chapter().marinesCount());
            };

            return sortParam.type() == SortingType.ASCENDING ? compareToResult : -compareToResult;
        });
    }

}
