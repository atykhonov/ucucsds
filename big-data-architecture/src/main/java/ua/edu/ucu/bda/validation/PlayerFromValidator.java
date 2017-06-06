package ua.edu.ucu.bda.validation;

import org.springframework.stereotype.Component;

@Component
public class PlayerFromValidator extends PlayerValidator {

    public static final String ERROR_COLUMN_NAME = "errorPlayerFromDoesntExist";

    public static final String PLAYER_COLUMN_NAME = "from";

    @Override
    public String getErrorColumnName() {
        return ERROR_COLUMN_NAME;
    }

    @Override
    public String getPlayerColumnName() {
        return PLAYER_COLUMN_NAME;
    }
}
