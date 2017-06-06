package ua.edu.ucu.bda.appender.column;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.appender.Appender;

import java.util.List;

@Service
public class FootballColumnsAppender implements Appender {

    @Autowired
    private List<ColumnAppender> columnAppenders;

    @Override
    public DataFrame apply(DataFrame dataFrame) {
        for (ColumnAppender appender : getColumnAppenders()) {
            dataFrame = appender.apply(dataFrame);
        }
        return dataFrame;
    }

    public List<ColumnAppender> getColumnAppenders() {
        return columnAppenders;
    }
}
