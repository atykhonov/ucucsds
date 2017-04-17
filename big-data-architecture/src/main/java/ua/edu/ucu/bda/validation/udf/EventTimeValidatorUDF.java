package ua.edu.ucu.bda.validation.udf;

import org.apache.spark.sql.api.java.UDF1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.utils.MatchTimeFormatException;
import ua.edu.ucu.bda.utils.MatchTimeParserUtils;

import javax.annotation.PostConstruct;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class EventTimeValidatorUDF extends ValidatorUDF implements UDF1<String, Boolean> {

    private static final Integer MAX_MINUTES_IN_GAME = 90;

    @Autowired
    private MatchTimeParserUtils matchTimeParser;

    @Override
    @PostConstruct
    public void registerUDF() {
        registerUDF(this);
    }

    @Override
    public Boolean call(String eventTime) throws Exception {
        try {
            Integer minutes = matchTimeParser.parseMinutes(eventTime);
            if (minutes > MAX_MINUTES_IN_GAME) {
                return true;
            }
        } catch (MatchTimeFormatException e) {
            return true;
        }
        return false;
    }
}
