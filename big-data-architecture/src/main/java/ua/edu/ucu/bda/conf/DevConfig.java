package ua.edu.ucu.bda.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SQLConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by demi on 4/8/17.
 */
@Configuration
@Profile("DEV")
public class DevConfig {

    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("football").setMaster("local[*]");
        return sparkConf;
    }

    @Bean
    public SQLConf sqlConf() {
        SQLConf sqlConf = new SQLConf();
        return sqlConf;
    }
}
