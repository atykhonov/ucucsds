package ua.edu.ucu.bda.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchTimeParserUtilsTest extends BaseTest {

    @Autowired
    private MatchTimeParserUtils parser;

    @Test
    public void testParseMinutes() {
        String matchTime = "10:00";

        Integer minutes = parser.parseMinutes(matchTime);

        Assert.assertEquals(new Integer(10), minutes);
    }

    @Test(expected = MatchTimeFormatException.class)
    public void testParseInvalidTime() {
        String invalidMatchTime = "2022";

        parser.parseMinutes(invalidMatchTime);
    }

    @Test(expected = MatchTimeFormatException.class)
    public void testParseInvalidMinutes() {
        String invalidMinutes = "ab:cd";

        parser.parseMinutes(invalidMinutes);
    }
}
