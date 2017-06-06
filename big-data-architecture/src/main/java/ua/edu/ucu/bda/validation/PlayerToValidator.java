package ua.edu.ucu.bda.validation;

import org.springframework.stereotype.Component;

@Component
public class PlayerToValidator extends PlayerValidator {

    public static final String ERROR_COLUMN_NAME = "errorPlayerToDoesntExist";

    public static final String PLAYER_COLUMN_NAME = "to";

    @Override
    public String getErrorColumnName() {
        return ERROR_COLUMN_NAME;
    }

    @Override
    public String getPlayerColumnName() {
        return PLAYER_COLUMN_NAME;
    }
}
