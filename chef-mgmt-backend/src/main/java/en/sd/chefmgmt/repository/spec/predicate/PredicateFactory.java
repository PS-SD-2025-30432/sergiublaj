package en.sd.chefmgmt.repository.spec.predicate;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PredicateFactory {

    private static final List<PredicateStrategy> strategies = List.of(
            new StringPredicateStrategy(),
            new NumberPredicateStrategy(),
            new DatePredicateStrategy()
    );

    public static Optional<Predicate> createPredicate(
            String field,
            Object value,
            Root<?> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return strategies.stream()
                .map(strategy -> strategy.createPredicate(field, value, root, criteriaBuilder))
                .filter(Optional::isPresent)
                .map(Optional::get).findFirst();
    }
}
