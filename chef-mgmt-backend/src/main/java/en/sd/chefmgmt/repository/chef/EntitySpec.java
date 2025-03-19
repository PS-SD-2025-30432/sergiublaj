package en.sd.chefmgmt.repository.chef;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public abstract class EntitySpec<Entity, EntityFilterDTO> {

    public Specification<Entity> createSpecification(EntityFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = getFieldValues(filter).entrySet().stream()
                    .map(entry -> createPredicate(entry.getKey(), entry.getValue(), root, criteriaBuilder))
                    .flatMap(Optional::stream)
                    .toList();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Map<String, Object> getFieldValues(EntityFilterDTO filter) {
        return getFilterableFields().stream()
                .map(field -> Map.entry(field, getFieldValue(filter, field)))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Optional<Object> getFieldValue(EntityFilterDTO filter, String fieldName) {
        try {
            Field field = filter.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return Optional.ofNullable(field.get(filter));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + fieldName, e);
        }
    }

    private Optional<Predicate> createPredicate(
            String field,
            Object value,
            Root<Entity> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return value instanceof String strValue
                ? Optional.of(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + strValue.toLowerCase() + "%"))
                : Optional.of(criteriaBuilder.equal(root.get(field), value));
    }

    protected abstract List<String> getFilterableFields();
}
