package ua.edu.ucu.bda;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.edu.ucu.bda.conf.ApplicationConfig;

import javax.xml.bind.DatatypeConverter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by demi on 4/9/17.
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        System.out.println("Hello World!");
        JavaSparkContext sc = context.getBean(JavaSparkContext.class);
        JavaRDD<String> rdd = sc.textFile("data/football/rawData.txt");
        rdd.persist(StorageLevel.MEMORY_AND_DISK());

        SQLContext sqlContext = context.getBean(SQLContext.class);
        /*
        StructField[] fields = new StructField[]{
                DataTypes.createStructField("line", DataTypes.StringType, false)
        };
        sqlContext.createDataFrame(rdd.rdd(), DataTypes.createStructType(fields));
        */
        DataFrame dataFrame = eventsRddAsDataFrame(sqlContext, rdd);
        System.out.println("Printing the schema...");
        dataFrame.printSchema();
        dataFrame.show();

        long count = rdd.count();
        System.out.println("count = " + count);

        /*
        JavaRDD<Event> trips = rdd.map(line -> {
            String[] arr = line.split(";");
            return new Trip(arr[0], arr[1], Integer.parseInt(arr[2]));
        });
        */

        // trips.persist(StorageLevel.MEMORY_AND_DISK());

        EventTypeServiceImpl service = context.getBean(EventTypeServiceImpl.class);
        System.out.print(service.getEventTypeById(3));

        // http://spark.apache.org/docs/latest/sql-programming-guide.html
    }

    private static DataFrame eventsRddAsDataFrame(SQLContext sqlContext, JavaRDD<String> eventsJavaRDD) {
        StructType schema = DataTypes
                .createStructType(new StructField[] {
                        DataTypes.createStructField(
                                "code", DataTypes.IntegerType, false),
                        DataTypes.createStructField(
                                "from", DataTypes.StringType, false),
                        DataTypes.createStructField(
                                "to", DataTypes.StringType, false),
                        DataTypes.createStructField(
                                "eventTime", DataTypes.TimestampType, false),
                        DataTypes.createStructField(
                                "stadion", DataTypes.StringType, false),
                        DataTypes.createStructField(
                                "startTime", DataTypes.TimestampType, false)
                });

        JavaRDD<Row> rowData = eventsJavaRDD.map(new Function<String, String[]>() {
            @Override
            public String[] call(String line) throws Exception {
                return line.split(";");
            }
        }).map(new Function<String[], Row>() {

            private String parseValue(String value) {
                String result = "";
                try {
                    String[] parts = value.split("=");
                    if (parts.length == 2) {
                        result = parts[1];
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new RuntimeException("Invalid value: " + value, e);
                }
                return result;
            }

            private Timestamp parseTime(String value) {
                Calendar calendar = GregorianCalendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                try {
                    Date date = sdf.parse(value);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return new Timestamp(calendar.get(Calendar.MILLISECOND));
            }

            @Override
            public Row call(String[] r) throws Exception {
                if (r.length == 6) {
                    String code = parseValue(r[0]);
                    String from = parseValue(r[1]);
                    String to = parseValue(r[2]);
                    String eventTime = parseValue(r[3]);
                    String stadion = parseValue(r[4]);
                    String startTime = parseValue(r[5]);
                    return RowFactory.create(
                            Integer.parseInt(code),
                            from,
                            to,
                            parseTime(eventTime),
                            stadion,
                            parseTime(startTime));
                }
                return RowFactory.create();
            }
        });

        return sqlContext.createDataFrame(rowData, schema);
    }
}
