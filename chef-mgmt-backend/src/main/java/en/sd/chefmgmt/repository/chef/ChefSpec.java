package en.sd.chefmgmt.repository.chef;

import java.util.List;

import en.sd.chefmgmt.dto.chef.ChefFilterDTO;
import en.sd.chefmgmt.model.ChefEntity;
import en.sd.chefmgmt.repository.spec.EntitySpec;
import en.sd.chefmgmt.repository.spec.predicate.PredicateFactory;

public class ChefSpec extends EntitySpec<ChefEntity, ChefFilterDTO> {

    public ChefSpec(PredicateFactory predicateFactory) {
        super(predicateFactory);
    }

    @Override
    protected List<String> getFilterableFields() {
        return List.of("name", "email", "rating", "birthDate");
    }
}
