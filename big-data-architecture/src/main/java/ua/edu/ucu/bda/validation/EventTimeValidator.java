package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.validation.udf.EventTimeValidatorUDF;

import static org.apache.spark.sql.functions.column;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class EventTimeValidator extends DataFrameValidator {

    private static final String EVENT_TIME_COLUMN_NAME = "eventTime";

    private static final String ERROR_COLUMN_NAME = "errorEventTime";

    @Autowired
    EventTimeValidatorUDF validatorUDF;

    @Override
    public DataFrame validate(DataFrame dataFrame) {
        return dataFrame.withColumn(
                getErrorColumnName(),
                validatorUDF.callUDF(column(EVENT_TIME_COLUMN_NAME)));
    }

    @Override
    public String getErrorColumnName() {
        return ERROR_COLUMN_NAME;
    }
}
