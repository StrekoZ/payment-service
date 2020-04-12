package lv.dp.education.ps.common;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Mapper {

    private MapperFactory mapperFactory;

    public <T> T map(Object object, Class<T> targetClass) {
        return mapperFactory.getMapperFacade().map(object, targetClass);
    }

    @PostConstruct
    public void init() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }
}