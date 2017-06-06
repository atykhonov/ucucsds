package ua.edu.ucu.bda.appender.column;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;
import java.util.List;

public class CountryColumnAppenderTest extends BaseTest {

    @Autowired
    private Country1ColumnAppender country1ColumnAppender;

    @Autowired
    private Country2ColumnAppender country2ColumnAppender;

    private String player;

    @Before
    public void setUp() {
        super.setUp();
        player = (String) userConfig.teamPlayers.keySet().toArray()[0];
    }

    @Test
    public void testCountry1ColumnIsAppended() {
        String colName = "from";
        String[] columnNames = new String[] {colName};
        userConfig.columnNames = Arrays.asList(columnNames);
        List<String> list = Arrays.asList(colName + "=" + player);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = country1ColumnAppender.apply(dataFrame);

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(country1ColumnAppender.getDestinationColumnName(), df.columns()[1]);
        Assert.assertEquals(userConfig.teamPlayers.get(player), df.first().getString(1));
    }

    @Test
    public void testCountry2ColumnIsAppended() {
        String colName = "to";
        String[] columnNames = new String[] {colName};
        userConfig.columnNames = Arrays.asList(columnNames);
        List<String> list = Arrays.asList(colName + "=" + player);
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame dataFrame = dataFrameCreator.createDataFrame(rdd);

        DataFrame df = country2ColumnAppender.apply(dataFrame);

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(country2ColumnAppender.getDestinationColumnName(), df.columns()[1]);
        Assert.assertEquals(userConfig.teamPlayers.get(player), df.first().getString(1));
    }
}
