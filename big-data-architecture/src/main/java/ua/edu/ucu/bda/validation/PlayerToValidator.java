package ua.edu.ucu.bda.validation;

import org.springframework.stereotype.Component;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class PlayerToValidator extends PlayerValidator {

    private static final String ERROR_COLUMN_NAME = "errorPlayerToDoesntExist";

    private static final String PLAYER_COLUMN_NAME = "to";

    @Override
    public String getErrorColumnName() {
        return ERROR_COLUMN_NAME;
    }

    @Override
    public String getPlayerColumnName() {
        return PLAYER_COLUMN_NAME;
    }
}
