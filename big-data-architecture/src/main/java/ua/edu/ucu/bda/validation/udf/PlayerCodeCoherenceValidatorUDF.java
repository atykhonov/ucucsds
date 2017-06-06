package ua.edu.ucu.bda.validation.udf;

import org.apache.spark.sql.api.java.UDF2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlayerCodeCoherenceValidatorUDF extends ValidatorUDF implements UDF2<String, String, Boolean> {

    @Override
    public Boolean call(String code, String playerTo) throws Exception {
        if ("1".equals(code) || "2".equals(code) || "5".equals(code) || "7".equals(code)) {
            if (!"".equals(playerTo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @PostConstruct
    public void registerUDF() {
        registerUDF(this);
    }
}