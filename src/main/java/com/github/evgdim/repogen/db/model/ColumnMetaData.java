package com.github.evgdim.repogen.db.model;

/**
 * Created by evgeni on 1/19/2017.
 */
public class ColumnMetaData {
    private String name;
    private String dbDataType;
    private Class javaDataType;

    public ColumnMetaData(String name, String dbDataType) {
        this.name = name;
        this.dbDataType = dbDataType;
        this.javaDataType = getJavaClass(dbDataType);
    }

    private static Class getJavaClass(String dataType) {
        switch (dataType){
            case "NUMBER":
                return Long.class;
            case "VARCHAR2":
                return String.class;
            default:
                return String.class;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbDataType() {
        return dbDataType;
    }

    public void setDbDataType(String dbDataType) {
        this.dbDataType = dbDataType;
    }

    public Class getJavaDataType() {
        return javaDataType;
    }

    public void setJavaDataType(Class javaDataType) {
        this.javaDataType = javaDataType;
    }

    public String getJavaDataTypeName() {
        return javaDataType.getSimpleName();
    }
}
