package ua.edu.ucu.bda;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.storage.StorageLevel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.edu.ucu.bda.conf.ApplicationConfig;
import ua.edu.ucu.bda.validation.FootballDataFrameValidator;

/**
 * Created by demi on 4/15/17.
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Application application = context.getBean(Application.class);
        application.oneTwoThreeFire(); //  !!!
    }
}
