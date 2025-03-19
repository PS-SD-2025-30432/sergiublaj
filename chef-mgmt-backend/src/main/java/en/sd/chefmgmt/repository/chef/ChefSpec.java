package en.sd.chefmgmt.repository.chef;

import java.util.List;

import en.sd.chefmgmt.dto.chef.ChefFilterDTO;
import en.sd.chefmgmt.model.ChefEntity;
import en.sd.chefmgmt.repository.spec.EntitySpec;

public class ChefSpec extends EntitySpec<ChefEntity, ChefFilterDTO> {

    @Override
    protected List<String> getFilterableFields() {
        return List.of("name", "email", "rating", "birthDate");
    }
}
