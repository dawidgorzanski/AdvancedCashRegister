package cashregister.modules;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ModulesManager {
    private static ApplicationContext context;
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized)
            return;

        context = new ClassPathXmlApplicationContext("Spring.xml");
        initialized = true;
    }

    public static <T> T getObjectByType(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> List<T> getObjectsByType(Class<T> beanClass) {
        Map<String, T> results = context.getBeansOfType(beanClass);
        List<T> list = new ArrayList<T>(results.values());
        return list;
    }
}
