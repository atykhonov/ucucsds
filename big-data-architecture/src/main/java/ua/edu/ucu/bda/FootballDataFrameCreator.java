package ua.edu.ucu.bda;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * Created by demi on 4/15/17.
 */
@Component
public class FootballDataFrameCreator implements Serializable {

    private static final String FOOTBALL_ROW_DATA_FILENAME = "data/football/rawData.txt";

    @Autowired
    private transient SQLContext sqlContext;

    @Autowired
    private transient JavaSparkContext sparkContext;

    @Autowired
    private DataRowCreator dataRowCreator;

    @Autowired
    private UserConfig userConfig;

    public DataFrame createDataFrame() {
        JavaRDD<String> rdd = sparkContext.textFile(FOOTBALL_ROW_DATA_FILENAME);
        rdd = rdd.filter(line -> !line.isEmpty());
        JavaRDD<Row> rowRdd = rdd.map(dataRowCreator::createRowFromLine);
        List<String> columnNames = userConfig.columnNames;
        StructField[] fields = new StructField[columnNames.size()];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = DataTypes.createStructField(columnNames.get(i), DataTypes.StringType, true);
        }
        return sqlContext.createDataFrame(rowRdd, DataTypes.createStructType(fields));
    }
}
