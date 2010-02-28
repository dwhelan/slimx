package fitnesse.slimx.spring.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import fitnesse.slimx.spring.Autowire;

public class StartTransaction extends Autowire {

    @Autowired
    protected HibernateTransactionManager transactionManager;

    public StartTransaction() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionManager.getTransaction(definition);
    }
}