package ua.edu.ucu.bda.validation.udf;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ucu.bda.infra.UDF;

/**
 * Created by demi on 4/17/17.
 */
public abstract class ValidatorUDF extends UDF {

    @Autowired
    private SQLContext sqlContext;

    public void registerUDF(UDF1 udf) {
        sqlContext.udf().register(getUDFName(), udf, DataTypes.BooleanType);
    }

    public void registerUDF(UDF2 udf) {
        sqlContext.udf().register(getUDFName(), udf, DataTypes.BooleanType);
    }
}
