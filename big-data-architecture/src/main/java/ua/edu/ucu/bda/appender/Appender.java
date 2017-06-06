package ua.edu.ucu.bda.appender;

import org.apache.spark.sql.DataFrame;

public interface Appender {

    DataFrame apply(DataFrame dataFrame);
}
