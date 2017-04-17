package ua.edu.ucu.bda.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by demi on 4/15/17.
 */
public class FileMapperUtils {

    public static Map<String, String> getMap(String line) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = line.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length > 1) {
                map.put(keyValue[0], keyValue[1]);
            } else {
                map.put(keyValue[0], "");
            }
        }
        return map;
    }
}
