package ua.edu.ucu.bda.appender.column;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.FootballDataFrameCreator;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;
import java.util.List;

public class HalfColumnAppenderTest extends BaseTest {

    @Autowired
    private HalfColumnAppender halfColumnAppender;

    @Autowired
    private FootballDataFrameCreator dataFrameCreator;

    @Test
    public void testColumnIsAppended() {
        String colName = halfColumnAppender.EVENT_TIME_COLUMN_NAME;
        String[] columnNames = new String[] {colName};
        userConfig.columnNames = Arrays.asList(columnNames);
        String eventTime = "10:10";
        List<String> list = Arrays.asList(colName + "=" + eventTime);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = halfColumnAppender.apply(dataFrame);

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(halfColumnAppender.HALF_COLUMN_NAME, df.columns()[1]);
        Assert.assertEquals(1, df.first().getInt(1));
    }
}
