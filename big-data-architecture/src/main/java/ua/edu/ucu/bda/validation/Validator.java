package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;

public interface Validator {

    DataFrame validate(DataFrame dataFrame);
}
