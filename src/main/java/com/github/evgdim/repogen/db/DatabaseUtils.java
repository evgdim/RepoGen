package com.github.evgdim.repogen.db;

import com.github.evgdim.repogen.db.model.ColumnMetaData;
import com.github.evgdim.repogen.db.model.TableMetaData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeni on 1/19/2017.
 */
public class DatabaseUtils {

    public static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    public static final String PASS = "survey";
    public static final String USER = "survey";

    private DatabaseUtils() { }

    public static TableMetaData getTableMetaData(final String tableName) throws SQLException, ClassNotFoundException {
//        Class.forName("oracle.jdbc.driver.OracleDriver");
//        TableMetaData tableMetaData = new TableMetaData(tableName, new ArrayList<>());
//        try(
//                Connection conn = DriverManager.getConnection(URL, USER, PASS);
//                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ALL_TAB_COLS WHERE TABLE_NAME = ? ORDER BY COLUMN_ID");
//                ){
//            stmt.setString(1, tableName);
//            ResultSet rs = stmt.executeQuery();
//            while(rs.next()){
//                String dataType = rs.getString("DATA_TYPE");
//                tableMetaData.getColumns().add(new ColumnMetaData(rs.getString("COLUMN_NAME"), dataType));
//            }
//        }
//        return tableMetaData;

        final List<TableMetaData> tabData = SqlTemplate.executeQuery("SELECT * FROM ALL_TAB_COLS WHERE TABLE_NAME = ? ORDER BY COLUMN_ID",
                new Object[]{tableName},
                rs -> {
                    TableMetaData tableMetaData = new TableMetaData(tableName, new ArrayList<>());
                    String dataType = null;
                    try {
                        dataType = rs.getString("DATA_TYPE");
                        tableMetaData.getColumns().add(new ColumnMetaData(rs.getString("COLUMN_NAME"), dataType));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return tableMetaData;
                });
        return tabData.get(0);
    }

    public static List<String> getTables(String schema) throws SQLException, ClassNotFoundException {
        List<String> tableNames = SqlTemplate.executeQuery("select * from all_tables where owner = 'SURVEY'", new Object[]{}, rs -> {
            try {
                return rs.getString("TABLE_NAME");
            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
        });
        return tableNames;
    }
}
