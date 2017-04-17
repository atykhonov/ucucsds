package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.validation.udf.PlayerValidatorUDF;

import static org.apache.spark.sql.functions.column;

/**
 * Created by demi on 4/17/17.
 */
public abstract class PlayerValidator extends DataFrameValidator {

    public abstract String getPlayerColumnName();

    @Autowired
    private PlayerValidatorUDF validatorUDF;

    @Override
    public DataFrame validate(DataFrame dataFrame) {
        return dataFrame.withColumn(
                getErrorColumnName(), validatorUDF.callUDF(column(getPlayerColumnName())));
    }
}
