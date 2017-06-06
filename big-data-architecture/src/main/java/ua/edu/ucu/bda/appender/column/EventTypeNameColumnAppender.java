package ua.edu.ucu.bda.appender.column;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.appender.column.udf.EventTypeNameColumnAppenderUDF;

import static org.apache.spark.sql.functions.column;

@Service
public class EventTypeNameColumnAppender implements ColumnAppender {

    public static final String EVENT_TYPE_NAME_COLUMN_NAME = "eventTypeName";

    public static final String EVENT_TYPE_COLUMN_NAME = "code";

    @Autowired
    private EventTypeNameColumnAppenderUDF appenderUDF;

    @Override
    public DataFrame apply(DataFrame dataFrame) {
        return dataFrame.withColumn(
                EVENT_TYPE_NAME_COLUMN_NAME,
                appenderUDF.callUDF(column(EVENT_TYPE_COLUMN_NAME)));
    }
}
