package ua.edu.ucu.bda.validation.udf;

import org.apache.spark.sql.api.java.UDF1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.UserConfig;

import javax.annotation.PostConstruct;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class PlayerValidatorUDF extends ValidatorUDF implements UDF1<String, Boolean> {

    @Autowired
    private UserConfig userConfig;

    @Override
    public Boolean call(String player) throws Exception {
        return ! userConfig.teamPlayers.containsKey(player);
    }

    @Override
    @PostConstruct
    public void registerUDF() {
        registerUDF(this);
    }
}
