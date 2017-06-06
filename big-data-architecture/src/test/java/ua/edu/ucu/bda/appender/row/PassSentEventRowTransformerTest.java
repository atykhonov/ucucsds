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

public class PassSentEventRowTransformerTest extends BaseTest {

    @Autowired
    private PassSentEventRowTransformer rowTransformer;

    @Test
    public void testRowTransformation() {
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

        DataFrame df = rowTransformer.transform(dataFrame);

        List<Row> rows = df.takeAsList(10);
        Assert.assertEquals(1, rows.size());
        Row r = rows.get(0);
        Assert.assertEquals((new Integer(4)).toString(), r.getString(0));
        Assert.assertEquals(playerTo, r.getString(1));
        Assert.assertEquals(playerFrom, r.getString(2));
    }
}
