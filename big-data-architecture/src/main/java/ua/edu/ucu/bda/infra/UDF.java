package ua.edu.ucu.bda.infra;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.functions;

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
