package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Arrays;

public class PlayerCodeCoherenceValidatorTest extends BaseTest {

    @Autowired
    private PlayerCodeCoherenceValidator validator;

    @Before
    public void setUp() {
        super.setUp();
        String[] columnNames = new String[] {
                PlayerCodeCoherenceValidator.CODE_COLUMN_NAME,
                PlayerCodeCoherenceValidator.TO_COLUMN_NAME,
        };
        userConfig.columnNames = Arrays.asList(columnNames);
    }

    @Test
    public void testValidCodeCoherence() {
        String row = createRow("");

        DataFrame df = validator.validate(createDataFrame(row));

        Assert.assertEquals(3, df.columns().length);
        Assert.assertEquals(validator.ERROR_COLUMN_NAME, df.columns()[2]);
        Assert.assertFalse(df.first().getBoolean(2));
    }

    @Test
    public void testInvalidCodeCoherence() {
        String row = createRow("player");

        DataFrame df = validator.validate(createDataFrame(row));

        Assert.assertEquals(3, df.columns().length);
        Assert.assertEquals(validator.ERROR_COLUMN_NAME, df.columns()[2]);
        Assert.assertTrue(df.first().getBoolean(2));
    }

    private String createRow(String player) {
        String row = "";
        row += PlayerCodeCoherenceValidator.CODE_COLUMN_NAME + "=" + 1;
        row += ";";
        row += PlayerCodeCoherenceValidator.TO_COLUMN_NAME + "=" + player;
        return row;
    }
}
