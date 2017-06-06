package ua.edu.ucu.bda;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EventType implements Serializable {

    private Integer id;

    private String name;
}
