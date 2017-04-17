package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.utils.ColumnUtils;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.functions.col;

/**
 * Created by demi on 4/15/17.
 */
@Service
public class FootballDataFrameValidator implements Validator {

    @Autowired
    private List<DataFrameValidator> validators;

    @Autowired
    private ColumnUtils columnUtils;

    @Override
    public DataFrame validate(DataFrame dataFrame) {
        for (DataFrameValidator validator : validators) {
            dataFrame = validator.validate(dataFrame);
        }
        return dataFrame;
    }

    public DataFrame getValidData(DataFrame dataFrame) {
        Column column = null;
        for (DataFrameValidator validator : validators) {
            Column errorColumn = col(validator.getErrorColumnName());
            Column c = errorColumn.equalTo(false);
            if (column != null) {
                column = column.and(c);
            } else {
                column = c;
            }
        }
        dataFrame = dataFrame.filter(column);

        dataFrame = dataFrame.select(columnUtils.createColumns());

        return dataFrame;
    }

    public DataFrame getInvalidData(DataFrame dataFrame) {
        Column column = null;
        for (DataFrameValidator validator : validators) {
            Column c = col(validator.getErrorColumnName()).equalTo(true);
            if (column != null) {
                column = column.or(c);
            } else {
                column = c;
            }
        }
        return dataFrame.filter(column);
    }
}