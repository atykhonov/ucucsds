package ua.edu.ucu.bda.appender.column;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.List;

public class FootballColumnsAppenderTest extends BaseTest {

    @Autowired
    private FootballColumnsAppender columnsAppender;

    @Autowired
    private Country1ColumnAppender country1ColumnAppender;

    @Autowired
    private Country2ColumnAppender country2ColumnAppender;

    @Autowired
    private EventTypeNameColumnAppender eventTypeNameColumnAppender;

    @Autowired
    private HalfColumnAppender halfColumnAppender;

    @Test
    public void testAppendersArePopulated() {
        List<ColumnAppender> appenders = columnsAppender.getColumnAppenders();
        Assert.assertEquals(4, appenders.size());
        Assert.assertTrue(appenders.indexOf(country1ColumnAppender) != -1);
        Assert.assertTrue(appenders.indexOf(country2ColumnAppender) != -1);
        Assert.assertTrue(appenders.indexOf(eventTypeNameColumnAppender) != -1);
        Assert.assertTrue(appenders.indexOf(halfColumnAppender) != -1);
    }
}
