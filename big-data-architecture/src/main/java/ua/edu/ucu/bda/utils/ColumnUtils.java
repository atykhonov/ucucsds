package ua.edu.ucu.bda.utils;

import org.apache.spark.sql.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.UserConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class ColumnUtils implements Serializable {

    @Autowired
    private UserConfig userConfig;

    public Column[] createColumns() {
        List<String> columnNames = userConfig.columnNames;
        Column[] columns = new Column[columnNames.size()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = col(columnNames.get(i));
        }
        return columns;
    }

    public Object[] createColumnValuesFromMap(Map<String, String> map) {
        List<String> columnValues = new ArrayList<>();
        for (String columnName: userConfig.columnNames) {
            columnValues.add(map.get(columnName));
        }
        return columnValues.toArray();
    }
}
