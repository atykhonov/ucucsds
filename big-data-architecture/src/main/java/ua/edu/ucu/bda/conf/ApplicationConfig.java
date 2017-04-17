package ua.edu.ucu.bda.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by demi on 4/9/17.
 */
@Configuration
@ComponentScan(basePackages = {"ua.edu.ucu.bda"})
@PropertySource("classpath:football_columns.properties")
public class ApplicationConfig {

    @Autowired
    private SparkConf sparkConf;

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(sparkConf);
    }

    @Bean
    public SQLContext sqlContext() {
        return new SQLContext(sc());
    }
}
