package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FootballDataFrameValidatorTest extends BaseTest {

    @Autowired
    private FootballDataFrameValidator validator;

    @Autowired
    private EventTimeValidator eventTimeValidator;

    @Autowired
    private PlayerFromValidator playerFromValidator;

    @Autowired
    private PlayerToValidator playerToValidator;

    @Autowired
    private PlayerCodeCoherenceValidator codeCoherenceValidator;

    private DataFrame dataFrame;

    @Before
    public void setUp() {
        super.setUp();
        dataFrame = createDataFrameWithErrors(validator.getValidators());
    }

    @Test
    public void testValidatorsArePopulated() {
        List<DataFrameValidator> validators = validator.getValidators();

        Assert.assertEquals(4, validators.size());
        Assert.assertTrue(validators.indexOf(eventTimeValidator) != -1);
        Assert.assertTrue(validators.indexOf(playerFromValidator) != -1);
        Assert.assertTrue(validators.indexOf(playerToValidator) != -1);
        Assert.assertTrue(validators.indexOf(codeCoherenceValidator) != -1);
    }

    @Test
    public void testGetValidData() {
        DataFrame df = validator.getValidData(dataFrame, false);

        List<Row> origRowList = dataFrame.takeAsList(10);
        Assert.assertEquals(5, origRowList.size());
        List<Row> validatedRowList = df.takeAsList(10);
        Assert.assertEquals(1, validatedRowList.size());
        Row validatedRow = validatedRowList.get(0);
        Assert.assertFalse(validatedRow.getBoolean(5));
        Assert.assertFalse(validatedRow.getBoolean(6));
        Assert.assertFalse(validatedRow.getBoolean(7));
        Assert.assertFalse(validatedRow.getBoolean(8));
    }

    @Test
    public void testGetInvalidData() {
        DataFrame df = validator.getInvalidData(dataFrame);

        List<Row> origRowList = dataFrame.takeAsList(10);
        Assert.assertEquals(5, origRowList.size());
        List<Row> validatedRowList = df.takeAsList(10);
        Assert.assertEquals(4, validatedRowList.size());
        Assert.assertTrue(
                getRowValues(validatedRowList.get(0)).indexOf("true") != -1);
        Assert.assertTrue(
                getRowValues(validatedRowList.get(1)).indexOf("true") != -1);
        Assert.assertTrue(
                getRowValues(validatedRowList.get(2)).indexOf("true") != -1);
        Assert.assertTrue(
                getRowValues(validatedRowList.get(3)).indexOf("true") != -1);
    }

    private List<String> getRowValues(Row row) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < row.length(); i++) {
            Object value = row.get(i);
            if (value != null) {
                result.add(value.toString());
            } else {
                result.add(null);
            }
        }
        return result;
    }
}