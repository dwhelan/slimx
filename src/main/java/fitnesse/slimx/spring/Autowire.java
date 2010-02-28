package fitnesse.slimx.spring;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class Autowire {

    private static AutowireCapableBeanFactory factory;

    static {
        ClassPathXmlApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("/applicationContext-test.xml");
        factory = testApplicationContext.getAutowireCapableBeanFactory();
    }
 
    public Autowire() {
        autowire(this);
    }
    
    public static void autowire(Object object) {
        factory.autowireBeanProperties(object, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
    }
    
    public static Object createBean(Class<?> clazz) {
        return factory.createBean(clazz, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
    }
}
