package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.Map;

public class ValidationSummaryTest extends BaseTest {

    @Autowired
    private ValidationSummary validationSummary;

    @Test
    public void testValidationSummary() {
        DataFrame dataFrame = createDataFrameWithErrors(validationSummary.getValidators());
        Map<String, Long> summary = validationSummary.getSummary(dataFrame);

        Assert.assertEquals(
                new Long(1), summary.get(EventTimeValidator.ERROR_COLUMN_NAME));
        Assert.assertEquals(
                new Long(2), summary.get(PlayerCodeCoherenceValidator.ERROR_COLUMN_NAME));
        Assert.assertEquals(
                new Long(3), summary.get(PlayerFromValidator.ERROR_COLUMN_NAME));
        Assert.assertEquals(
                new Long(4), summary.get(PlayerToValidator.ERROR_COLUMN_NAME));
    }
}
