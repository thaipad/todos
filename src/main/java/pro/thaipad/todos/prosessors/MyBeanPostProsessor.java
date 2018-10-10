package pro.thaipad.todos.prosessors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProsessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(MyBeanPostProsessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        log.info("postProsessAfterInit: " + o);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
