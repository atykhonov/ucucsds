package ua.edu.ucu.bda.appender.column.udf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.EventType;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.HashMap;

public class EventTypeNameColumnAppenderUDFTest extends BaseTest {

    @Autowired
    private EventTypeNameColumnAppenderUDF columnAppenderUDF;

    @Autowired
    private UserConfig userConfig;

    @Test
    public void testCall() throws Exception {
        HashMap eventTypes = new HashMap();
        Integer eventId = 1;
        String eventName = "pass";
        EventType eventType = new EventType(eventId, eventName);
        eventTypes.put(eventId, eventType);
        userConfig.eventTypes = eventTypes;

        Assert.assertEquals(eventName, columnAppenderUDF.call(eventId.toString()));
    }

    @Test
    public void testCallWithNoneInteger() throws Exception {
        String eventName = columnAppenderUDF.call("lorem");

        Assert.assertEquals("<unknown>", eventName);
    }

    @Test
    public void testCallWithUnknownEventId() throws Exception {
        userConfig.eventTypes = new HashMap<>();

        String unknownTypeId = "4";
        String eventName = columnAppenderUDF.call(unknownTypeId);

        Assert.assertEquals("<unknown>", eventName);
    }
}
