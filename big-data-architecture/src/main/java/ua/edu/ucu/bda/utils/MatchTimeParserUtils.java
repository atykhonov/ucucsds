package ua.edu.ucu.bda.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MatchTimeParserUtils implements Serializable {

    public Integer parseMinutes(String matchTime) {
        Integer minutes = null;
        String[] pairs = matchTime.split(":");
        if (pairs.length != 2) {
            throw new MatchTimeFormatException();
        }
        try {
            minutes = Integer.parseInt(pairs[0]);
        } catch (NumberFormatException e) {
            throw new MatchTimeFormatException();
        }
        return minutes;
    }
}
