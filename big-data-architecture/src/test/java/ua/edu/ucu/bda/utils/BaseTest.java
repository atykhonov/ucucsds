package ua.edu.ucu.bda.utils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ucu.bda.EventType;
import ua.edu.ucu.bda.FootballDataFrameCreator;
import ua.edu.ucu.bda.UserConfig;
import ua.edu.ucu.bda.conf.ApplicationConfig;
import ua.edu.ucu.bda.validation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ua.edu.ucu.bda.constants.Profiles.DEV;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles(DEV)
@Ignore
public class BaseTest {

    @Autowired
    protected UserConfig userConfig;

    @Autowired
    protected JavaSparkContext sc;

    @Autowired
    protected FootballDataFrameCreator dataFrameCreator;

    @Autowired
    protected FootballDataFrameValidator footballDataFrameValidator;

    @Autowired
    protected EventTimeValidator eventTimeValidator;

    private Map<Integer, EventType> userEventTypes;

    private List<String> userColumnNames;

    private Map<String,String> userTeamPlayers;

    protected DataFrame createDataFrame(String row) {
        List<String> list = Arrays.asList(row);
        return createDataFrame(list);
    }

    protected DataFrame createDataFrame(List<String> rows) {
        JavaRDD<String> rdd = sc.parallelize(rows);
        return dataFrameCreator.createDataFrame(rdd);
    }

    protected DataFrame createDataFrameWithErrors(List<DataFrameValidator> validators) {
        List<String> columnNames = new ArrayList<>();
        for (DataFrameValidator validator : validators) {
            columnNames.add(validator.getErrorColumnName());
        }
        List<String> rows = new ArrayList<>();
        rows.add(createRow(
                "1",
                "player1",
                "player2",
                "100:00"));
        rows.add(createRow(
                "1",
                "player1",
                "player2",
                "10:00"));
        rows.add(createRow(
                "1",
                "player1",
                "",
                "10:00"));
        rows.add(createRow(
                "3",
                "Igor Akinfeev",
                "Roman Shirokov2",
                "10:00"));
        rows.add(createRow(
                "3",
                "Igor Akinfeev",
                "Roman Shirokov",
                "10:00"));

        DataFrame dataFrame = createDataFrame(rows);

        dataFrame = footballDataFrameValidator.validate(dataFrame);

        return dataFrame;
    }

    private String createRow(String eventTypeId, String playerFrom, String playerTo, String eventTime) {
        String row = "";
        row += PlayerCodeCoherenceValidator.CODE_COLUMN_NAME + "=" + eventTypeId;
        row += ";";
        row += PlayerFromValidator.PLAYER_COLUMN_NAME + "=" + playerFrom;
        row += ";";
        row += PlayerToValidator.PLAYER_COLUMN_NAME + "=" + playerTo;
        row += ";";
        row += EventTimeValidator.EVENT_TIME_COLUMN_NAME + "=" + eventTime;
        return row;
    }

    @Before
    public void setUp() {
        userEventTypes = userConfig.eventTypes;
        userColumnNames = userConfig.columnNames;
        userTeamPlayers = userConfig.teamPlayers;
    }

    @After
    public void tearDown() {
        userConfig.eventTypes = userEventTypes;
        userConfig.columnNames = userColumnNames;
        userConfig.teamPlayers = userTeamPlayers;
    }
}