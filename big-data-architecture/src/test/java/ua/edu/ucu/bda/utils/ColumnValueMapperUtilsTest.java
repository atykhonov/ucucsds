package ua.edu.ucu.bda.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ColumnValueMapperUtilsTest extends BaseTest {

    @Test
    public void testMapping() {
        String line = "col1=val1;col2=val2;col3=val3";
        Map<String, String> map = ColumnValueMapperUtils.getMap(line);

        Assert.assertEquals("val1", map.get("col1"));
        Assert.assertEquals("val2", map.get("col2"));
        Assert.assertEquals("val3", map.get("col3"));
    }
}
