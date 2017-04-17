package ua.edu.ucu.bda.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.validation.udf.PlayerValidatorUDF;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class PlayerFromValidator extends PlayerValidator {

    private static final String ERROR_COLUMN_NAME = "errorPlayerFromDoesntExist";

    private static final String PLAYER_COLUMN_NAME = "from";

    @Override
    public String getErrorColumnName() {
        return ERROR_COLUMN_NAME;
    }

    @Override
    public String getPlayerColumnName() {
        return PLAYER_COLUMN_NAME;
    }
}
