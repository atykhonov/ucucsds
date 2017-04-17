package ua.edu.ucu.bda.appender.column;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.appender.Appender;

import java.util.List;

/**
 * Created by demi on 4/17/17.
 */
@Service
public class FootballColumnsAppender implements Appender {

    @Autowired
    private List<ColumnAppender> columnAppenders;

    @Override
    public DataFrame apply(DataFrame dataFrame) {
        for (ColumnAppender appender : columnAppenders) {
            dataFrame = appender.apply(dataFrame);
        }
        return dataFrame;
    }
}
