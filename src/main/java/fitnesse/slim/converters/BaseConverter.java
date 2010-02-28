package fitnesse.slim.converters;

import fitnesse.slim.Slim;
import fitnesse.slim.Converter;

public abstract class BaseConverter implements Converter {

    protected Class<?> clazz;

    public BaseConverter(Class<?> clazz) {
        this.clazz = clazz;
        Slim.addConverter(clazz, this);
    }    
}
