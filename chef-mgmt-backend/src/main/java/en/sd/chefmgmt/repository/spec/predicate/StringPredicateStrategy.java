package en.sd.chefmgmt.repository.spec.predicate;

import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class StringPredicateStrategy implements PredicateStrategy<String> {

    @Override
    public Optional<Predicate> createPredicate(
            String field,
            String value,
            Root<?> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return Optional.of(criteriaBuilder.like(
                criteriaBuilder.lower(root.get(field)),
                "%" + value.toLowerCase() + "%"
        ));
    }
}