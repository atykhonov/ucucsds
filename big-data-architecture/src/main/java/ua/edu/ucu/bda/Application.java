package ua.edu.ucu.bda;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ucu.bda.appender.column.FootballColumnsAppender;
import ua.edu.ucu.bda.appender.row.FootballRowsAppender;
import ua.edu.ucu.bda.validation.FootballDataFrameValidator;
import ua.edu.ucu.bda.validation.ValidationSummary;

import java.util.Map;

/**
 * Created by demi on 4/17/17.
 */
@Component
public class Application {

    @Autowired
    private FootballDataFrameCreator dataFrameCreator;

    @Autowired
    private FootballDataFrameValidator dataFrameValidator;

    @Autowired
    private FootballColumnsAppender columnsAppender;

    @Autowired
    private FootballRowsAppender rowsAppender;

    @Autowired
    private ValidationSummary validationSummary;

    public void oneTwoThreeFire() {
        /*
         One, Two, Three, Fire!!!
         */
        DataFrame dataFrame = dataFrameCreator.createDataFrame();
        dataFrame.persist(StorageLevel.MEMORY_AND_DISK());

        dataFrame = dataFrameValidator.validate(dataFrame);
        DataFrame invalidDataFrame = dataFrameValidator.getInvalidData(dataFrame);
        showDataFrame(
                "Data frame with invalid data: ", invalidDataFrame);
        showValidationSummary(validationSummary.getSummary(invalidDataFrame));

        dataFrame = dataFrameValidator.getValidData(dataFrame);
        showDataFrame("Data frame with valid data: ", dataFrame);

        dataFrame = rowsAppender.apply(dataFrame);
        showDataFrame("Data frame with appended rows: ", dataFrame);

        dataFrame = columnsAppender.apply(dataFrame);
        showDataFrame("Data frame with appended columns: ", dataFrame);
    }

    private void showValidationSummary(Map<String, Long> summary) {
        long maxErrors = 0;
        String maxError = null;
        System.out.println("Validation summary: ");
        for (String error: summary.keySet()) {
            Long errorCount = summary.get(error);
            if (errorCount > maxErrors) {
                maxErrors = errorCount;
                maxError = error;
            }
            System.out.format("%s: %s\n", error, summary.get(error));
        }
        System.out.format(
                "Maximum errors %s has been found for '%s'.\n\n", maxErrors, maxError);
    }

    private void showDataFrame(String message, DataFrame dataFrame) {
        System.out.println(message);
        dataFrame.show();
    }
}
