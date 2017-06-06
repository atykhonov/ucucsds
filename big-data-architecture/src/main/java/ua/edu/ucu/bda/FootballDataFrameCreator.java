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
import scala.util.parsing.combinator.testing.Str;

import java.io.Serializable;
import java.util.List;

@Component
public class FootballDataFrameCreator implements Serializable {

    @Autowired
    private transient SQLContext sqlContext;

    @Autowired
    private DataRowCreator dataRowCreator;

    @Autowired
    private UserConfig userConfig;

    public DataFrame createDataFrame(JavaRDD<String> rdd) {
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
