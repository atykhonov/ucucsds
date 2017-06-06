package ua.edu.ucu.bda.appender.row;


import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;
import java.util.List;

public class ReversedPassRowAppenderTest extends BaseTest {

    @Autowired
    private ReversedPassRowAppender rowAppender;

    @Test
    public void testRowIsAppended() {
        String[] columnNames = new String[] {
                PassSentEventRowTransformer.CODE_COLUMN_NAME,
                PassSentEventRowTransformer.FROM_COLUMN_NAME,
                PassSentEventRowTransformer.TO_COLUMN_NAME,
        };
        userConfig.columnNames = Arrays.asList(columnNames);
        Integer eventTypeId = 3;
        String playerFrom = "Artyom Dzyuba";
        String playerTo = "Igor Akinfeev";
        String row = "";
        row += PassSentEventRowTransformer.CODE_COLUMN_NAME + "=" + eventTypeId;
        row += ";";
        row += PassSentEventRowTransformer.FROM_COLUMN_NAME + "=" + playerFrom;
        row += ";";
        row += PassSentEventRowTransformer.TO_COLUMN_NAME + "=" + playerTo;
        List<String> list = Arrays.asList(row);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = rowAppender.apply(dataFrame);

        List<Row> rows = df.takeAsList(10);
        Assert.assertEquals(2, rows.size());
        Row r1 = rows.get(0);
        Assert.assertEquals((new Integer(3)).toString(), r1.getString(0));
        Assert.assertEquals(playerFrom, r1.getString(1));
        Assert.assertEquals(playerTo, r1.getString(2));
        Row r2 = rows.get(1);
        Assert.assertEquals((new Integer(4)).toString(), r2.getString(0));
        Assert.assertEquals(playerTo, r2.getString(1));
        Assert.assertEquals(playerFrom, r2.getString(2));
    }
}
