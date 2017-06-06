package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.utils.ColumnUtils;

import java.util.List;

import static org.apache.spark.sql.functions.col;

@Service
public class FootballDataFrameValidator implements Validator {

    @Autowired
    private List<DataFrameValidator> validators;

    @Autowired
    private ColumnUtils columnUtils;

    @Override
    public DataFrame validate(DataFrame dataFrame) {
        for (DataFrameValidator validator : getValidators()) {
            dataFrame = validator.validate(dataFrame);
        }
        return dataFrame;
    }

    public List<DataFrameValidator> getValidators() {
        return validators;
    }

    public DataFrame getValidData(DataFrame dataFrame, boolean origColumns) {
        Column column = null;
        for (DataFrameValidator validator : validators) {
            Column errorColumn = col(validator.getErrorColumnName());
            Column c = errorColumn.equalTo(false);
            c = c.or(errorColumn.equalTo("false"));
            if (column != null) {
                column = column.and(c);
            } else {
                column = c;
            }
        }
        dataFrame = dataFrame.filter(column);

        if (origColumns) {
            dataFrame = dataFrame.select(columnUtils.createColumns());
        }

        return dataFrame;
    }

    public DataFrame getInvalidData(DataFrame dataFrame) {
        Column column = null;
        for (DataFrameValidator validator : validators) {
            Column errorColumn = col(validator.getErrorColumnName());
            Column c = errorColumn.equalTo(true);
            c = c.or(errorColumn.equalTo("true"));
            if (column != null) {
                column = column.or(c);
            } else {
                column = c;
            }
        }
        return dataFrame.filter(column);
    }
}