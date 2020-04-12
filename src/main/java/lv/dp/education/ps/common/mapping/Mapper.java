package lv.dp.education.ps.common.mapping;


import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Mapper {
    @Autowired(required = false)
    private Set<MappingConfiguration> configurations;

    private MapperFactory mapperFactory;

    public <T> T map(Object object, Class<T> targetClass) {
        return mapperFactory.getMapperFacade().map(object, targetClass);
    }

    @PostConstruct
    public void init() {
        mapperFactory = new DefaultMapperFactory.Builder().build();

        if (configurations != null) {
            configurations.forEach(config -> config.addClassMap(mapperFactory));
        }
    }
}