package ua.edu.ucu.bda.appender.column.udf;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.EventType;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.infra.UDF;

import javax.annotation.PostConstruct;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class EventTypeNameColumnAppenderUDF extends UDF implements UDF1<String, String> {

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private UserConfig userConfig;

    @Override
    public String call(String typeId) throws Exception {
        try {
            Integer eventTypeId = Integer.parseInt(typeId);
            EventType eventType = userConfig.eventTypes.get(eventTypeId);
            if (eventType != null) {
                return eventType.getName();
            }
        } catch (NumberFormatException e) {
            // Ignore the exception.
        }
        return "<unknown>";
    }

    @Override
    @PostConstruct
    public void registerUDF() {
        sqlContext.udf().register(getUDFName(), this, DataTypes.StringType);
    }
}
