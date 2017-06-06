package ua.edu.ucu.bda.appender.column.udf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

public class HalfColumnAppenderUDFTest extends BaseTest {

    @Autowired
    private HalfColumnAppenderUDF appenderUDF;

    @Test
    public void testCallFirstHalf() throws Exception {
        Integer half = appenderUDF.call("10:10");

        Assert.assertEquals(new Integer(1), half);
    }

    @Test
    public void testCallSecondHalf() throws Exception {
        Integer half = appenderUDF.call("60:60");

        Assert.assertEquals(new Integer(2), half);
    }
}
