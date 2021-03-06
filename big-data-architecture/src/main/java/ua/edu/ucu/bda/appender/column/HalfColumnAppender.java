package ua.edu.ucu.bda.appender.column;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.appender.column.udf.HalfColumnAppenderUDF;

import static org.apache.spark.sql.functions.column;

@Service
public class HalfColumnAppender implements ColumnAppender {

    public static final String HALF_COLUMN_NAME = "half";

    public static final String EVENT_TIME_COLUMN_NAME = "eventTime";

    @Autowired
    private HalfColumnAppenderUDF appenderUDF;

    @Override
    public DataFrame apply(DataFrame dataFrame) {
        return dataFrame.withColumn(
                HALF_COLUMN_NAME,
                appenderUDF.callUDF(column(EVENT_TIME_COLUMN_NAME)));
    }
}
