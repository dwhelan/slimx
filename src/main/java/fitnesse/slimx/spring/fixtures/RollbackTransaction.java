package fitnesse.slimx.spring.fixtures;

import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fitnesse.slimx.spring.Autowire;

public class RollbackTransaction extends Autowire {

    @Autowired
    protected SessionFactory sessionFactory;

    public RollbackTransaction() throws SQLException {
        sessionFactory.getCurrentSession().close();
    }
}
