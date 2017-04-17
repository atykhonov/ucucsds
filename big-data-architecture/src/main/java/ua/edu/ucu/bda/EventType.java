package ua.edu.ucu.bda;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

/**
 * Created by demi on 4/9/17.
 */
@Data
@AllArgsConstructor
public class EventType implements Serializable {

    private Integer id;

    private String name;
}