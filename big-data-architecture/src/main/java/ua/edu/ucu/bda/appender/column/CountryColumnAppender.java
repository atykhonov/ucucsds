package ua.edu.ucu.bda.appender.column;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.appender.column.udf.CountryColumnAppenderUDF;

import static org.apache.spark.sql.functions.column;

/**
 * Created by demi on 4/17/17.
 */
public abstract class CountryColumnAppender implements ColumnAppender {

    @Autowired
    private CountryColumnAppenderUDF appenderUDF;

    public abstract String getSourceColumnName();

    public abstract String getDestinationColumnName();

    public DataFrame apply(DataFrame dataFrame) {
        return dataFrame.withColumn(
                getDestinationColumnName(),
                appenderUDF.callUDF(column(getSourceColumnName())));
    }
}
