package ua.edu.ucu.bda.appender;

import org.apache.spark.sql.DataFrame;

/**
 * Created by demi on 4/17/17.
 */
public interface Appender {

    DataFrame apply(DataFrame dataFrame);
}
