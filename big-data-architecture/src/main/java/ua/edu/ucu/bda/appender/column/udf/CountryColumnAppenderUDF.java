package ua.edu.ucu.bda.appender.column.udf;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.infra.UDF;

import javax.annotation.PostConstruct;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class CountryColumnAppenderUDF extends UDF implements UDF1<String, String> {

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private UserConfig userConfig;

    @Override
    @PostConstruct
    public void registerUDF() {
        sqlContext.udf().register(getUDFName(), this, DataTypes.StringType);
    }

    @Override
    public String call(String player) throws Exception {
        String command = userConfig.teamPlayers.get(player);
        if (command == null) {
            command = "<unknown>";
        }
        return command;
    }
}
