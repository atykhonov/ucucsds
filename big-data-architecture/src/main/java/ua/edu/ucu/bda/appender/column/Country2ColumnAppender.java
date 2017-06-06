package ua.edu.ucu.bda.appender.column;

import org.springframework.stereotype.Service;

@Service
public class Country2ColumnAppender extends CountryColumnAppender {

    private static final String SOURCE_COLUMN_NAME = "to";

    private static final String DESTINATION_COLUMN_NAME = "command2";

    @Override
    public String getSourceColumnName() {
        return SOURCE_COLUMN_NAME;
    }

    @Override
    public String getDestinationColumnName() {
        return DESTINATION_COLUMN_NAME;
    }
}
