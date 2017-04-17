package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.validation.udf.PlayerCodeCoherenceValidatorUDF;

import static org.apache.spark.sql.functions.column;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class PlayerCodeCoherenceValidator extends DataFrameValidator {

    private static final String ERROR_COLUMN_NAME = "errorPlayerCodeCoherence";

    private static final String CODE_COLUMN_NAME = "code";

    private static final String TO_COLUMN_NAME = "to";

    @Autowired
    private PlayerCodeCoherenceValidatorUDF validatorUDF;

    @Override
    public DataFrame validate(DataFrame dataFrame) {
        return dataFrame.withColumn(
                getErrorColumnName(),
                validatorUDF.callUDF(column(CODE_COLUMN_NAME), column(TO_COLUMN_NAME)));
    }

    @Override
    public String getErrorColumnName() {
        return ERROR_COLUMN_NAME;
    }
}
