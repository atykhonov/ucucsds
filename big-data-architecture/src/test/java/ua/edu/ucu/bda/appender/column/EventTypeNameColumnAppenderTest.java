package ua.edu.ucu.bda.appender.column;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.EventType;
import ua.edu.ucu.bda.FootballDataFrameCreator;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EventTypeNameColumnAppenderTest extends BaseTest {

    @Autowired
    private FootballDataFrameCreator dataFrameCreator;

    @Autowired
    private EventTypeNameColumnAppender columnAppender;

    @Before
    public void setUp() {
        super.setUp();
        HashMap eventTypes = new HashMap();
        Integer eventId = 1;
        String eventName = "pass";
        EventType eventType = new EventType(eventId, eventName);
        eventTypes.put(eventId, eventType);
        userConfig.eventTypes = eventTypes;
    }

    @Test
    public void testColumnIsAppended() {
        String colName = "code";
        String[] columnNames = new String[] {colName};
        userConfig.columnNames = Arrays.asList(columnNames);
        Integer eventTypeId = 1;
        List<String> list = Arrays.asList(colName + "=" + eventTypeId);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = columnAppender.apply(dataFrame);

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(columnAppender.EVENT_TYPE_NAME_COLUMN_NAME, df.columns()[1]);
        Assert.assertEquals(
                userConfig.eventTypes.get(eventTypeId).getName(),
                df.first().getString(1));
    }
}
