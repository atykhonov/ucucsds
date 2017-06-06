package ua.edu.ucu.bda.validation.udf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class PlayerValidatorUDFTest extends BaseTest {

    @Autowired
    private PlayerValidatorUDF validatorUDF;

    @Test
    public void testValidPlayer() throws Exception {
        Map<String, String> map = new HashMap<>();
        String playerName = "player";
        map.put("player", "country");
        userConfig.teamPlayers = map;

        Assert.assertFalse(validatorUDF.call(playerName));
    }

    @Test
    public void testInvalidPlayer() throws Exception {
        Map<String, String> map = new HashMap<>();
        userConfig.teamPlayers = new HashMap<>();

        Assert.assertTrue(validatorUDF.call("player"));
    }
}
