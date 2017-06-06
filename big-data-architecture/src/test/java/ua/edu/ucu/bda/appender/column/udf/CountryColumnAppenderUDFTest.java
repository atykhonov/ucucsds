package ua.edu.ucu.bda.appender.column.udf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.utils.BaseTest;

import java.util.HashMap;

public class CountryColumnAppenderUDFTest extends BaseTest {

    @Autowired
    private CountryColumnAppenderUDF columnAppenderUDF;

    @Autowired
    private UserConfig userConfig;

    @Test
    public void testCallWithUnknownPlayer() throws Exception {
        userConfig.teamPlayers = new HashMap<>();
        String unknownPlayer = "Eric Kim";
        String command = columnAppenderUDF.call(unknownPlayer);

        Assert.assertEquals("<unknown>", command);
    }

    @Test
    public void testCallWithKnownPlayer() throws Exception {
        HashMap players = new HashMap();
        String player = "Eric Kim";
        String country = "USA";
        players.put(player, country);
        userConfig.teamPlayers = players;

        Assert.assertEquals(country, columnAppenderUDF.call(player));
    }
}
