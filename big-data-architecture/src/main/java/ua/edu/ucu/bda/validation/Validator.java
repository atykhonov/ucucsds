package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;

/**
 * Created by demi on 4/15/17.
 */
public interface Validator {

    DataFrame validate(DataFrame dataFrame);
}
