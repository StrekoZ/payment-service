package lv.dp.education.ps.common.mapping;

import ma.glasnost.orika.MapperFactory;

public class DefaultMappingConfiguration implements MappingConfiguration {
    private Class sourceClass;
    private Class targetClass;

    public DefaultMappingConfiguration(Class sourceClass, Class targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    @Override
    public void addClassMap(MapperFactory mapperFactory) {
        mapperFactory.classMap(sourceClass(), targetClass())
                .byDefault()
                .register();
    }

    protected Class sourceClass() {
        return sourceClass;
    }

    protected Class targetClass() {
        return targetClass;
    }
}
