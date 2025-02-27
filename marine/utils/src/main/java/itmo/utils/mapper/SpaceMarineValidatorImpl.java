package itmo.utils.mapper;


import itmo.utils.dto.SpaceMarineCreateRequest;
import itmo.utils.dto.SpaceMarineUpdateRequest;
import itmo.utils.exceptions.SpaceMarineValidationException;
import jakarta.ejb.Stateless;
import org.jboss.ejb3.annotation.Pool;


@Stateless
@Pool("slsb-strict-max-pool")
public class SpaceMarineValidatorImpl implements SpaceMarineValidator {
    @Override
    public void validate(SpaceMarineCreateRequest spaceMarineCreateRequest) {
        if (spaceMarineCreateRequest.name() == null || spaceMarineCreateRequest.name().isEmpty())
            throw new SpaceMarineValidationException("Имя обязательно!");
        if (spaceMarineCreateRequest.coordinates() == null || spaceMarineCreateRequest.coordinates().x() == null || spaceMarineCreateRequest.coordinates().y() > 829)
            throw new SpaceMarineValidationException("Координаты невалидны!");
        if (spaceMarineCreateRequest.health() != null && spaceMarineCreateRequest.health() <= 0)
            throw new SpaceMarineValidationException("Здоровье должно быть более 0!");
        if (spaceMarineCreateRequest.weaponType() == null)
            throw new SpaceMarineValidationException("Тип оружия обязателен!");
        if (spaceMarineCreateRequest.meleeWeapon() == null)
            throw new SpaceMarineValidationException("Холодное оружие обязательно!");
        if (spaceMarineCreateRequest.chapterName() == null || spaceMarineCreateRequest.chapterName().isEmpty())
            throw new SpaceMarineValidationException("Название главы не должно быть пустым!");
    }

    @Override
    public void validate(SpaceMarineUpdateRequest spaceMarineUpdateRequest) {
        if (spaceMarineUpdateRequest.name() == null || spaceMarineUpdateRequest.name().isEmpty())
            throw new SpaceMarineValidationException("Имя обязательно!");
        if (spaceMarineUpdateRequest.coordinates() == null || spaceMarineUpdateRequest.coordinates().x() == null || spaceMarineUpdateRequest.coordinates().y() > 829)
            throw new SpaceMarineValidationException("Координаты невалидны!");
        if (spaceMarineUpdateRequest.health() <= 0)
            throw new SpaceMarineValidationException("Здоровье должно быть более 0!");
        if (spaceMarineUpdateRequest.weaponType() == null)
            throw new SpaceMarineValidationException("Тип оружия обязателен!");
        if (spaceMarineUpdateRequest.meleeWeapon() == null)
            throw new SpaceMarineValidationException("Холодное оружие обязательно!");
        if (spaceMarineUpdateRequest.chapterName() == null || spaceMarineUpdateRequest.chapterName().isEmpty())
            throw new SpaceMarineValidationException("Название главы не должно быть пустым!");
    }
}
