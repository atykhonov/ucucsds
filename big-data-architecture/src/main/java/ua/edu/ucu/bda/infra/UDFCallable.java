package ua.edu.ucu.bda.infra;

import org.apache.spark.sql.Column;

public interface UDFCallable {

    String getUDFName();

    Column callUDF(Column... columns);
}
