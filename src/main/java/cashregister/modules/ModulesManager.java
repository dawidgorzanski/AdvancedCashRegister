package cashregister.modules;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Manages all modules in application
 */
public class ModulesManager {
    private static ApplicationContext context;
    private static boolean initialized = false;

    /**
     * Initializes ApplicationContext from Spring.xml file
     */
    public static void initialize() {
        if (initialized)
            return;

        context = new ClassPathXmlApplicationContext("Spring.xml");
        initialized = true;
    }

    /**
     * Returns object kept by Spring.xml bean based on passed Class
     * @param beanClass class of kept object
     * @param <T> parameter that determines what type of object we want to get from Spring
     * @return desirable object of type T
     */
    public static <T> T getObjectByType(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * Returns all objects kept by Spring.xml beans based on passed Class
     * @param beanClass class of kept objects
     * @param <T> parameters that determines what type of objects we want to get from Spring
     * @return desirable List of T objects
     */
    public static <T> List<T> getObjectsByType(Class<T> beanClass) {
        Map<String, T> results = context.getBeansOfType(beanClass);
        List<T> list = new ArrayList<T>(results.values());
        return list;
    }
}
