package ua.edu.ucu.bda.validation.udf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

public class EventTimeValidatorUDFTest extends BaseTest {

    @Autowired
    private EventTimeValidatorUDF validatorUDF;

    @Test
    public void testCallWithValidTime() throws Exception {
        String eventTime = "10:00";

        Assert.assertFalse(validatorUDF.call(eventTime));
    }

    @Test
    public void testCallWithInvalidTime() throws Exception {
        String invalidEventTime = "120:00";

        Assert.assertTrue(validatorUDF.call(invalidEventTime));
    }
}
