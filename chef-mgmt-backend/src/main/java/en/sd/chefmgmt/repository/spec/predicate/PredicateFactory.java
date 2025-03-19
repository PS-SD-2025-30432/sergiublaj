package en.sd.chefmgmt.repository.spec.predicate;

import java.util.Map;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PredicateFactory {

    private final Map<Class<?>, PredicateStrategy<?>> strategies;

    public Optional<Predicate> createPredicate(
            String field,
            Object value,
            Root<?> root,
            CriteriaBuilder criteriaBuilder
    ) {
        PredicateStrategy<?> strategy = strategies.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(value.getClass()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        return strategy != null
                ? strategy.createPredicate(field, value, root, criteriaBuilder)
                : Optional.empty();
    }
}
