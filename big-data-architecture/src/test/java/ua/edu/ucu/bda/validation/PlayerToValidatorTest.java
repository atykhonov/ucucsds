package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class PlayerToValidatorTest extends BaseTest {

    @Autowired
    private PlayerToValidator validator;

    @Before
    public void setUp() {
        super.setUp();

        String[] columnNames = new String[] {
                PlayerToValidator.PLAYER_COLUMN_NAME,
                PlayerToValidator.ERROR_COLUMN_NAME,
        };
        userConfig.columnNames = Arrays.asList(columnNames);

        Map<String, String> map = new HashMap<>();
        map.put("player", "country");
        userConfig.teamPlayers = map;
    }

    @Test
    public void testValidPlayerTo() {
        String row = createRow("player");

        DataFrame df = validator.validate(createDataFrame(row));

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(PlayerToValidator.ERROR_COLUMN_NAME, df.columns()[1]);
        Assert.assertFalse(df.first().getBoolean(1));
    }

    @Test
    public void testInvalidPlayerFrom() {
        String row = createRow("invalid");

        DataFrame df = validator.validate(createDataFrame(row));

        Assert.assertEquals(2, df.columns().length);
        Assert.assertEquals(PlayerToValidator.ERROR_COLUMN_NAME, df.columns()[1]);
        Assert.assertTrue(df.first().getBoolean(1));
    }

    private String createRow(String player) {
        String row = "";
        row += PlayerToValidator.PLAYER_COLUMN_NAME + "=" + player;
        return row;
    }
}
