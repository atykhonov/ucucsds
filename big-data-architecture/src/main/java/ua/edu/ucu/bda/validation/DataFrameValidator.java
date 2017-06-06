package ua.edu.ucu.bda.validation;

import org.springframework.stereotype.Component;

@Component
public abstract class DataFrameValidator implements Validator {

    public abstract String getErrorColumnName();
}
