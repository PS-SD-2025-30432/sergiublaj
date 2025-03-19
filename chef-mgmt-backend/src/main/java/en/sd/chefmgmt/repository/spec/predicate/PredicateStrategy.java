package en.sd.chefmgmt.repository.spec.predicate;

import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PredicateStrategy {

    Optional<Predicate> createPredicate(String field, Object value, Root<?> root, CriteriaBuilder criteriaBuilder);
}