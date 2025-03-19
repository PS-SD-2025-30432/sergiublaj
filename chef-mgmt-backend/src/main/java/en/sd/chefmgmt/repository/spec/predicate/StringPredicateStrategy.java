package en.sd.chefmgmt.repository.spec.predicate;

import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StringPredicateStrategy implements PredicateStrategy<String> {

    @Override
    public Optional<Predicate> createPredicate(
            String field,
            Object value,
            Root<?> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return Optional.of(criteriaBuilder.like(
                criteriaBuilder.lower(root.get(field)),
                "%" + ((String) value).toLowerCase() + "%"
        ));
    }
}