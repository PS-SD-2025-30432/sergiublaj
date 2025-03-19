package en.sd.chefmgmt.repository.spec.predicate;

import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StringPredicateStrategy implements PredicateStrategy {

    @Override
    public Optional<Predicate> createPredicate(
            String field,
            Object value,
            Root<?> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return value instanceof String strValue
                ? Optional.of(criteriaBuilder.like(
                criteriaBuilder.lower(root.get(field)),
                "%" + strValue.toLowerCase() + "%"
        ))
                : Optional.empty();
    }
}