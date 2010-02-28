package fitnesse.slim.converters;

import static fitnesse.slimx.spring.Autowire.autowire;

import org.springframework.beans.factory.annotation.Autowired;

import com.intellimec.drivesync.dao.VehicleDAO;

public abstract class EntityConverter extends BaseConverter {

    @Autowired
    private VehicleDAO dao;
    
    public EntityConverter(Class<?> clazz) {
        super(clazz);
        autowire(this);
    }

    @Override
    public Object fromString(String id) {
        return dao.get(clazz, Long.parseLong(id));
    }

    @Override
    public String toString(Object entity) {
        return Long.toString(getId(entity));
    }
    
    protected abstract long getId(Object entity);
}
