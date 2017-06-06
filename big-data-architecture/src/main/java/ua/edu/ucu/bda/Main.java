package ua.edu.ucu.bda;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.edu.ucu.bda.conf.ApplicationConfig;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Application application = context.getBean(Application.class);
        application.exec();
    }
}
