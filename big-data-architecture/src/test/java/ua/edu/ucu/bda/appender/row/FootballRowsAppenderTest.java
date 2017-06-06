package ua.edu.ucu.bda.appender.row;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.List;

public class FootballRowsAppenderTest extends BaseTest {

    @Autowired
    private FootballRowsAppender rowsAppender;

    @Autowired
    private ReversedPassRowAppender reversedPassRowAppender;

    @Test
    public void testAppendersArePopulated() {
        List<RowAppender> appenders = rowsAppender.getRowAppenders();
        Assert.assertEquals(1, appenders.size());
        Assert.assertTrue(appenders.indexOf(reversedPassRowAppender) != -1);
    }
}
