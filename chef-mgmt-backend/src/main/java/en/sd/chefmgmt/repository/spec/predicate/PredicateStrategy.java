package en.sd.chefmgmt.repository.spec.predicate;

import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PredicateStrategy<Type> {

    Optional<Predicate> createPredicate(String field, Type value, Root<?> root, CriteriaBuilder criteriaBuilder);
}