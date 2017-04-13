package ua.edu.ucu.bda.properties;

import ua.edu.ucu.bda.Main;
import ua.edu.ucu.bda.conf.ApplicationConfig;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by demi on 4/13/17.
 */
public class EventTypes extends Hashtable<Integer, String> {

    private Properties properties;

    public EventTypes() {
        super();
        properties = new Properties();
    }

    public void load() throws IOException {
        properties.load(
                Main.class.getClassLoader()
                        .getResourceAsStream("event_types.properties"));
        Iterator iterator = properties.keySet().iterator();
        while (this.keySet().iterator().hasNext()) {
            String key = (String) iterator.next();

            System.out.println("Key: " + key);
        }
    }
}
