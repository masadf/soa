package itmo.spaceshipservice.domain.repositories.entities;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Converter
public class MarinesIdConverter implements AttributeConverter<Set<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Integer> integers) {
        return integers.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) return new HashSet<>();
        return Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toSet());
    }
}