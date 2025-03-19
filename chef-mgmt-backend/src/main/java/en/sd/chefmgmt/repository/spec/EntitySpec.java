package en.sd.chefmgmt.repository.spec;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import en.sd.chefmgmt.repository.spec.predicate.PredicateFactory;
import en.sd.chefmgmt.util.ReflectionUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public abstract class EntitySpec<Entity, EntityFilterDTO> {

    private final PredicateFactory predicateFactory;

    public Specification<Entity> createSpecification(EntityFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = getFieldValues(filter).entrySet().stream()
                    .map(e -> predicateFactory.createPredicate(e.getKey(), e.getValue(), root, criteriaBuilder))
                    .flatMap(Optional::stream)
                    .toList();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Map<String, Object> getFieldValues(EntityFilterDTO filter) {
        return getFilterableFields().stream()
                .map(field -> Map.entry(field, ReflectionUtil.getFieldValue(filter, field)))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected abstract List<String> getFilterableFields();
}
