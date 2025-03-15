package en.sd.chefmgmt.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import en.sd.chefmgmt.model.ChefEntity;

@Repository
public interface ChefRepository extends JpaRepository<ChefEntity, UUID> {

    List<ChefEntity> findAllByNameContaining(String name);
}
