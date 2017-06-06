package ua.edu.ucu.bda.appender.column.udf;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.infra.UDF;
import ua.edu.ucu.bda.utils.MatchTimeParserUtils;

import javax.annotation.PostConstruct;

@Component
public class HalfColumnAppenderUDF extends UDF implements UDF1<String, Integer> {

    private static final Integer THE_END_OF_FIRST_HALF = 45;

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private MatchTimeParserUtils matchTimeParser;

    @Override
    @PostConstruct
    public void registerUDF() {
        sqlContext.udf().register(getUDFName(), this, DataTypes.IntegerType);
    }

    @Override
    public Integer call(String eventTime) throws Exception {
        Integer minutes = matchTimeParser.parseMinutes(eventTime);
        if (minutes > THE_END_OF_FIRST_HALF) {
            return 2;
        }
        return 1;
    }
}
