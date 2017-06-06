package ua.edu.ucu.bda;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

@Component
public class UserConfig implements Serializable {

    private static final String EVENT_TYPES_PROPERTY_FILENAME = "event_types.properties";

    private static final String TEAMS_PROPERTIES_FILENAME = "teams.properties";

    public List<String> columnNames;

    public Map<String, String> teamPlayers = new HashMap<>();

    public Map<Integer, EventType> eventTypes = new HashMap<>();

    @Value("${columnNames}")
    private void setColumnNames(String[] columnNames) {
        this.columnNames = Arrays.asList(columnNames);
    }

    @PostConstruct
    private void initTeams() {
        Properties properties = loadPropertiesFromFile(TEAMS_PROPERTIES_FILENAME);
        for (Object key: properties.keySet()) {
            String line = (String) properties.get(key);
            String[] players = line.split(",");
            for (String player : players) {
                teamPlayers.put(player, (String) key);
            }
        }
    }

    @PostConstruct
    private void initEventTypes() {
        Properties properties = loadPropertiesFromFile(EVENT_TYPES_PROPERTY_FILENAME);
        for (Object key: properties.keySet()) {
            Integer eventId = Integer.parseInt ((String) key);
            String eventName = properties.getProperty(eventId.toString());
            EventType eventType = new EventType(eventId, eventName);
            eventTypes.put(eventId, eventType);
        }
    }

    @SneakyThrows
    private Properties loadPropertiesFromFile(String filename) {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
        return properties;
    }
}
