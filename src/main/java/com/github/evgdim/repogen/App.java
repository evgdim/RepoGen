package com.github.evgdim.repogen;

import com.github.evgdim.repogen.code.JavaGenUtils;
import com.github.evgdim.repogen.commons.Commons;
import com.github.evgdim.repogen.db.DatabaseUtils;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {
        System.out.println("Running...");
        System.out.print(JavaGenUtils.generateClassFromTable("USERS"));
        System.out.print(JavaGenUtils.generateRowMapperFromTable("USERS", Commons.underscoreToCamelCase("USERS")));
        System.out.print(DatabaseUtils.getTables("SURVEY"));
    }
}
