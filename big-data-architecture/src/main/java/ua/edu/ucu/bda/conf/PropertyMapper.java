package ua.edu.ucu.bda.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by demi on 4/13/17.
 */
@Component("PropertyMapper")
public class PropertyMapper {

    @Autowired
    ApplicationContext applicationContext;

    public HashMap<String, Object> startWith(String qualifier, String startWith) {
        return startWith(qualifier, startWith, false);
    }

    public HashMap<String, Object> startWith(String qualifier, String startWith, boolean removeStartWith) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        Object obj = applicationContext.getBean(qualifier);
        if (obj instanceof Properties) {
            Properties mobileProperties = (Properties) obj;

            if (mobileProperties != null) {
                for (Map.Entry<Object, Object> e : mobileProperties.entrySet()) {
                    Object oKey = e.getKey();
                    if (oKey instanceof String) {
                        String key = (String)oKey;
                        if (((String) oKey).startsWith(startWith)) {
                            if (removeStartWith)
                                key = key.substring(startWith.length());
                            result.put(key, e.getValue());
                        }
                    }
                }
            }
        }

        return result;
    }
}
