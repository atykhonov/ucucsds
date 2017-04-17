package ua.edu.ucu.bda.validation;

import org.springframework.stereotype.Component;

/**
 * Created by demi on 4/15/17.
 */
@Component
public abstract class DataFrameValidator implements Validator {

    public abstract String getErrorColumnName();
}
