package ua.edu.ucu.bda.utils;

import org.apache.spark.sql.Column;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.appender.row.PassSentEventRowTransformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ColumnUtilsTest extends BaseTest {

    @Autowired
    private ColumnUtils columnUtils;

    @Test
    public void testColumnsCreation() {
        String[] columnNames = new String[] {
                PassSentEventRowTransformer.CODE_COLUMN_NAME,
                PassSentEventRowTransformer.FROM_COLUMN_NAME,
                PassSentEventRowTransformer.TO_COLUMN_NAME,
        };
        userConfig.columnNames = Arrays.asList(columnNames);

        Column[] columns = columnUtils.createColumns();

        Assert.assertEquals("code", columns[0].toString());
        Assert.assertEquals("from", columns[1].toString());
        Assert.assertEquals("to", columns[2].toString());
    }

    @Test
    public void testColumnValuesAreCreatedFromMap() {
        String[] columnNames = new String[] {
                PassSentEventRowTransformer.CODE_COLUMN_NAME,
                PassSentEventRowTransformer.FROM_COLUMN_NAME,
                PassSentEventRowTransformer.TO_COLUMN_NAME,
        };
        userConfig.columnNames = Arrays.asList(columnNames);
        Map<String, String> map = new HashMap<>();
        String codeValue = "4";
        String playerFrom = "Artyom Dzyuba";
        String playerTo = "Igor Akinfeev";
        map.put(PassSentEventRowTransformer.CODE_COLUMN_NAME, codeValue);
        map.put(PassSentEventRowTransformer.FROM_COLUMN_NAME, playerFrom);
        map.put(PassSentEventRowTransformer.TO_COLUMN_NAME, playerTo);

        Object[] values = columnUtils.createColumnValuesFromMap(map);
        Assert.assertEquals(codeValue, values[0]);
        Assert.assertEquals(playerFrom, values[1]);
        Assert.assertEquals(playerTo, values[2]);
    }
}
