package ua.edu.ucu.bda.validation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;
import java.util.List;

public class EventTimeValidatorTest extends BaseTest {

    @Autowired
    private EventTimeValidator validator;

    @Test
    public void testValidEventTime() {
        String colName = EventTimeValidator.EVENT_TIME_COLUMN_NAME;
        String[] columnNames = new String[] {colName};
        userConfig.columnNames = Arrays.asList(columnNames);
        String eventTime = "10:00";
        List<String> list = Arrays.asList(colName + "=" + eventTime);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = validator.validate(dataFrame);

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(validator.ERROR_COLUMN_NAME, df.columns()[1]);
        Assert.assertFalse(df.first().getBoolean(1));
    }

    @Test
    public void testInvalidEventTime() {
        String colName = EventTimeValidator.EVENT_TIME_COLUMN_NAME;
        String[] columnNames = new String[] {colName};
        userConfig.columnNames = Arrays.asList(columnNames);
        String eventTime = "invalid";
        List<String> list = Arrays.asList(colName + "=" + eventTime);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = validator.validate(dataFrame);

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(validator.ERROR_COLUMN_NAME, df.columns()[1]);
        Assert.assertTrue(df.first().getBoolean(1));
    }
}
