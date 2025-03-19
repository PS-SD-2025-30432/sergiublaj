package en.sd.chefmgmt.repository.spec.predicate;

import java.time.ZonedDateTime;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DatePredicateStrategy implements PredicateStrategy {

    @Override
    public Optional<Predicate> createPredicate(
            String field,
            Object value,
            Root<?> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return value instanceof ZonedDateTime dateValue
                ? Optional.of(criteriaBuilder.equal(root.get(field), dateValue))
                : Optional.empty();
    }
}