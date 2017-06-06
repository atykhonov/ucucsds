package ua.edu.ucu.bda.appender.row;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.appender.Appender;

import java.util.List;

@Service
public class FootballRowsAppender implements Appender {

    @Autowired
    private List<RowAppender> rowAppenders;

    @Override
    public DataFrame apply(DataFrame dataFrame) {
        for (RowAppender appender : rowAppenders) {
            dataFrame = appender.apply(dataFrame);
        }
        return dataFrame;
    }

    public List<RowAppender> getRowAppenders() {
        return rowAppenders;
    }
}
