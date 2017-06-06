package ua.edu.ucu.bda;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.utils.ColumnUtils;
import ua.edu.ucu.bda.utils.ColumnValueMapperUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by demi on 4/15/17.
 */
@Service
public class DataRowCreator implements Serializable {

    @Autowired
    private ColumnUtils columnUtils;

    public Row createRowFromLine(String line) {
        Map<String, String> map = ColumnValueMapperUtils.getMap(line);
        return RowFactory.create(columnUtils.createColumnValuesFromMap(map));
    }
}
