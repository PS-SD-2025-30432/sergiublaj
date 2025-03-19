package en.sd.chefmgmt.config;

import en.sd.chefmgmt.controller.chef.ChefController;
import en.sd.chefmgmt.controller.chef.ChefControllerBean;
import en.sd.chefmgmt.mapper.ChefMapper;
import en.sd.chefmgmt.repository.chef.ChefRepository;
import en.sd.chefmgmt.repository.chef.ChefSpec;
import en.sd.chefmgmt.service.chef.ChefService;
import en.sd.chefmgmt.service.chef.ChefServiceBean;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ChefController chefController(ChefService chefService) {
        return new ChefControllerBean(chefService);
    }

    @Bean
    public ChefService chefService(
            ChefRepository chefRepository,
            ChefSpec chefSpec,
            ChefMapper chefMapper
    ) {
        return new ChefServiceBean(chefRepository, chefSpec, chefMapper);
    }

    @Bean
    public ChefSpec chefSpec() {
        return new ChefSpec();
    }

    @Bean
    public ChefMapper chefMapper() {
        return Mappers.getMapper(ChefMapper.class);
    }
}
