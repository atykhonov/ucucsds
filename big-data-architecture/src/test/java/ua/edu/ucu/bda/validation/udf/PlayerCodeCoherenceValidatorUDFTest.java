package ua.edu.ucu.bda.validation.udf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

public class PlayerCodeCoherenceValidatorUDFTest extends BaseTest {

    @Autowired
    private PlayerCodeCoherenceValidatorUDF validatorUDF;

    @Test
    public void testPlayerToIsEmpty() throws Exception {
        Assert.assertFalse(validatorUDF.call("1", ""));
        Assert.assertFalse(validatorUDF.call("2", ""));
        Assert.assertFalse(validatorUDF.call("3", ""));
        Assert.assertFalse(validatorUDF.call("4", ""));
        Assert.assertFalse(validatorUDF.call("5", ""));
        Assert.assertFalse(validatorUDF.call("6", ""));
        Assert.assertFalse(validatorUDF.call("7", ""));
    }

    @Test
    public void testPlayerToIsNotEmpty() throws Exception {
        Assert.assertTrue(validatorUDF.call("1", "pl"));
        Assert.assertTrue(validatorUDF.call("2", "pl"));
        Assert.assertFalse(validatorUDF.call("3", "pl"));
        Assert.assertFalse(validatorUDF.call("4", "pl"));
        Assert.assertTrue(validatorUDF.call("5", "pl"));
        Assert.assertFalse(validatorUDF.call("6", "pl"));
        Assert.assertTrue(validatorUDF.call("7", "pl"));
    }
}
