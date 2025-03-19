package en.sd.chefmgmt.config;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import en.sd.chefmgmt.controller.chef.ChefController;
import en.sd.chefmgmt.controller.chef.ChefControllerBean;
import en.sd.chefmgmt.mapper.ChefMapper;
import en.sd.chefmgmt.repository.chef.ChefRepository;
import en.sd.chefmgmt.repository.chef.ChefSpec;
import en.sd.chefmgmt.repository.spec.predicate.DatePredicateStrategy;
import en.sd.chefmgmt.repository.spec.predicate.NumberPredicateStrategy;
import en.sd.chefmgmt.repository.spec.predicate.PredicateFactory;
import en.sd.chefmgmt.repository.spec.predicate.PredicateStrategy;
import en.sd.chefmgmt.repository.spec.predicate.StringPredicateStrategy;
import en.sd.chefmgmt.service.chef.ChefService;
import en.sd.chefmgmt.service.chef.ChefServiceBean;
import en.sd.chefmgmt.util.ReflectionUtil;
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
    public ChefSpec chefSpec(PredicateFactory predicateFactory) {
        return new ChefSpec(predicateFactory);
    }

    @Bean
    public ChefMapper chefMapper() {
        return Mappers.getMapper(ChefMapper.class);
    }

    @Bean
    public PredicateStrategy<ZonedDateTime> datePredicateStrategy() {
        return new DatePredicateStrategy();
    }

    @Bean
    public PredicateStrategy<Number> numberPredicateStrategy() {
        return new NumberPredicateStrategy();
    }

    @Bean
    public PredicateStrategy<String> stringPredicateStrategy() {
        return new StringPredicateStrategy();
    }

    @Bean
    public PredicateFactory predicateFactory(List<PredicateStrategy<?>> strategies) {
        return new PredicateFactory(strategies.stream().collect(Collectors.toMap(
                ReflectionUtil::getGenericType,
                strategy -> strategy
        )));
    }
}
