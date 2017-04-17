package ua.edu.ucu.bda.validation;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.UserConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by demi on 4/17/17.
 */
@Service
public class ValidationSummary {

    @Autowired
    private List<DataFrameValidator> validators;

    public Map<String, Long> getSummary(DataFrame dataFrame) {
        Map<String, Long> summary = new HashMap<>();
        for (DataFrameValidator validator : validators) {
            String errorColumn = validator.getErrorColumnName();
            long errors = dataFrame
                    .javaRDD()
                    .filter(row -> row.getBoolean(row.fieldIndex(errorColumn)) == true).count();
            summary.put(errorColumn, errors);
        }
        return summary;
    }
}
