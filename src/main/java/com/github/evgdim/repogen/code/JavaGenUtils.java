package com.github.evgdim.repogen.code;

import com.github.evgdim.repogen.commons.Commons;
import com.github.evgdim.repogen.db.DatabaseUtils;
import com.github.evgdim.repogen.db.model.TableMetaData;
import com.squareup.javapoet.*;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import javax.lang.model.element.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by evgeni on 1/19/2017.
 */
public class JavaGenUtils {

    public static final String ROW_MAPPER = "RowMapper";

    public static String generateClassFromTable(String tableName) throws SQLException, ClassNotFoundException {
        TableMetaData tableMetaData = DatabaseUtils.getTableMetaData(tableName);
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(Commons.underscoreToCamelCase(tableName))
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Data.class);

        tableMetaData.getColumns().forEach(col -> {
            String colName = Commons.underscoreToCamelCase(col.getName());
            FieldSpec fieldSpec = FieldSpec.builder(col.getJavaDataType(), colName, Modifier.PRIVATE).build();
           builder.addField(fieldSpec);
        });

        TypeSpec typeSpec = builder.build();
        return typeSpec.toString();
    }

    public static String generateRowMapperFromTable(String tableName, String modelClassName) throws SQLException, ClassNotFoundException {
        ClassName className = ClassName.get("com.changeme",modelClassName);
        TableMetaData tableMetaData = DatabaseUtils.getTableMetaData(tableName);
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(modelClassName + ROW_MAPPER)
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterface(RowMapper.class);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("mapRow")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ResultSet.class,"rs")
                .addParameter(int.class, "i")
                .returns(className);

        methodBuilder.addStatement("$T item = new $T()", className, className);
        tableMetaData.getColumns().forEach(col -> {
            String setMethod = "set" + Commons.capitalize(
                                                        Commons.underscoreToCamelCase(
                                                                                col.getName()
                                                        )
                                        );
            methodBuilder.addStatement("item."+setMethod+"( rs.get"+col.getJavaDataTypeName()+"(\""+col.getName()+"\") )");
        });
        methodBuilder.addStatement("return item");
        builder.addMethod(methodBuilder.build());
        TypeSpec typeSpec = builder.build();
        return typeSpec.toString();
    }
}
