package ua.edu.ucu.bda.infra;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.functions;

/**
 * Created by demi on 4/17/17.
 */
public interface UDFCallable {

    String getUDFName();

    Column callUDF(Column... columns);
}
