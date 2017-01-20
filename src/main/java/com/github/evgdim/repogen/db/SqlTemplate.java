package com.github.evgdim.repogen.db;

import com.github.evgdim.repogen.db.model.ColumnMetaData;
import com.github.evgdim.repogen.db.model.TableMetaData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by evgeni on 1/20/2017.
 */
public class SqlTemplate {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    public static final String PASS = "survey";
    public static final String USER = "survey";

    public static <T> List<T> executeQuery(String sql, Object[] params, Function<ResultSet,T> mapper) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            for(int i = 0; i < params.length; i++){
                if(params[i] instanceof String){ stmt.setString(i + 1, (String)params[i]); }
                if(params[i] instanceof Long){ stmt.setLong(i + 1, (Long)params[i]); }
            }
            ResultSet rs = stmt.executeQuery();
            List<T> result = new ArrayList<T>();
            while(rs.next()){
                T row = mapper.apply(rs);
                result.add(row);
            }
            return result;
        }
    }
}
