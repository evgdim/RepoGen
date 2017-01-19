package com.github.evgdim.repogen.db;

import com.github.evgdim.repogen.db.model.ColumnMetaData;
import com.github.evgdim.repogen.db.model.TableMetaData;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by evgeni on 1/19/2017.
 */
public class DatabaseUtils {

    public static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    public static final String PASS = "survey";
    public static final String USER = "survey";

    private DatabaseUtils() { }

    public static TableMetaData getTableMetaData(final String tableName) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        TableMetaData tableMetaData = new TableMetaData(tableName, new ArrayList<>());
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ALL_TAB_COLS WHERE TABLE_NAME = ? ORDER BY COLUMN_ID");
                ){
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String dataType = rs.getString("DATA_TYPE");
                tableMetaData.getColumns().add(new ColumnMetaData(rs.getString("COLUMN_NAME"), dataType));
            }
        }
        return tableMetaData;
    }


}
