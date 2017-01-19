package com.github.evgdim.repogen.db.model;

import java.util.List;

/**
 * Created by evgeni on 1/19/2017.
 */
public class TableMetaData {
    private String name;
    private List<ColumnMetaData> columns;

    public TableMetaData(String name, List<ColumnMetaData> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnMetaData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMetaData> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "TableMetaData{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                '}';
    }
}
