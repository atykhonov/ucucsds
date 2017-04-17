package ua.edu.ucu.bda.appender.row;

import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ucu.bda.utils.ColumnUtils;

import static org.apache.spark.sql.functions.col;

/**
 * Created by demi on 4/17/17.
 */
@Service
public class ReversedPassRowAppender implements RowAppender {

    private static final String CODE_COLUMN_NAME = "code";

    @Autowired
    private PassSentEventRowTransformer transformer;

    @Autowired
    private ColumnUtils columnUtils;

    @Override
    public DataFrame apply(DataFrame dataFrame) {
        DataFrame passSentEvents = selectPassSentEvents(dataFrame);
        DataFrame transformedDataFrame = transformer.transform(passSentEvents);
        return appendRows(dataFrame, transformedDataFrame);
    }

    private DataFrame appendRows(DataFrame dataFrame, DataFrame passSentEvents) {
        return dataFrame.unionAll(passSentEvents);
    }

    private DataFrame selectPassSentEvents(DataFrame dataFrame) {
        return dataFrame.select(columnUtils.createColumns()).where(col(CODE_COLUMN_NAME).equalTo("3"));
    }
}
