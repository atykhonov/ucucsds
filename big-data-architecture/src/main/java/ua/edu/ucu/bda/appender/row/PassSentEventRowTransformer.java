package ua.edu.ucu.bda.appender.row;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.utils.ColumnUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PassSentEventRowTransformer implements Serializable {

    public static final String CODE_COLUMN_NAME = "code";

    public static final String FROM_COLUMN_NAME = "from";

    public static final String TO_COLUMN_NAME = "to";

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private ColumnUtils columnUtils;

    public DataFrame transform(DataFrame dataFrame) {

        List<String> columnNames = userConfig.columnNames;

        JavaRDD<Row> rdd = dataFrame.javaRDD();
        rdd = rdd.map(row -> {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                String columnValue = row.getString(i);
                if (CODE_COLUMN_NAME.equals(columnName)) {
                    columnValue = "4";
                }
                map.put(columnName, columnValue);
            }
            Object[] columnVals = columnUtils.createColumnValuesFromMap(map);
            List<Object> columnValues = Arrays.asList(columnVals);

            Integer fromIndex = columnNames.indexOf(FROM_COLUMN_NAME);
            String from = map.get(FROM_COLUMN_NAME);
            Integer toIndex = columnNames.indexOf(TO_COLUMN_NAME);
            String to = map.get(TO_COLUMN_NAME);
            Object[] values = columnValues.toArray();
            values[fromIndex] = to;
            values[toIndex] = from;

            return RowFactory.create(values);
        });

        StructField[] fields = new StructField[columnNames.size()];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = DataTypes.createStructField(columnNames.get(i), DataTypes.StringType, true);
        }
        return sqlContext.createDataFrame(rdd, DataTypes.createStructType(fields));
    }
}
