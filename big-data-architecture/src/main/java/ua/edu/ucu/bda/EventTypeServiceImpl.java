package ua.edu.ucu.bda;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by demi on 4/9/17.
 */
@Service
public class EventTypeServiceImpl implements EventTypeService {

    private static final String EVENT_TYPES_PROPERTY_FILENAME = "event_types.properties";

    private Map<Integer, EventType> eventTypes = new HashMap<>();

    public EventTypeServiceImpl() {
        loadEventTypes();
    }

    @Override
    public EventType getEventTypeById(Integer eventId) {
        return eventTypes.get(eventId);
    }

    private void loadEventTypes() {
        Properties properties = loadProperties();
        Iterator iterator = properties.keySet().iterator();
        while (iterator.hasNext()) {
            String eventId = (String) iterator.next();
            String eventName = properties.getProperty(eventId);
            EventType eventType = new EventType(new Integer(eventId), eventName);
            eventTypes.put(new Integer(eventId), eventType);
        }
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(
                    Main.class.getClassLoader()
                            .getResourceAsStream(EVENT_TYPES_PROPERTY_FILENAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}