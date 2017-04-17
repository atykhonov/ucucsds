package ua.edu.ucu.bda.infra;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.UserConfig;

/**
 * Created by demi on 4/17/17.
 */
public abstract class UDF implements UDFCallable, UDFRegistration {

    @Override
    public String getUDFName() {
        return this.getClass().getName();
    }

    @Override
    public Column callUDF(Column... columns) {
        return functions.callUDF(getUDFName(), columns);
    }
}
