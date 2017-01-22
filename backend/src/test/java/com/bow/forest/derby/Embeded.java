package com.bow.forest.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author vv
 * @since 2016/12/17.
 */
public class Embeded {

    public static void main(String[] args) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String dbName = "temp/vvDB";
        String connectionURL = "jdbc:derby:" + dbName + ";create=true";

        Connection conn = null;
        Statement s;
        PreparedStatement psInsert;
        ResultSet myWishes;
        String printLine = "  __________________________________________________";
        String createString = "CREATE TABLE WISH_LIST  "
                + "(WISH_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                + "   CONSTRAINT WISH_PK PRIMARY KEY, "
                + " ENTRY_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + " WISH_ITEM VARCHAR(32) NOT NULL) ";
        String answer;

        try {
            conn = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);

            s = conn.createStatement();
            if (!wwdChk4Table(conn)) {
                s.execute(createString);
            }
            psInsert = conn.prepareStatement("insert into WISH_LIST(WISH_ITEM) values (?)");

            psInsert.setString(1, "dream");
            psInsert.executeUpdate();
            myWishes = s.executeQuery("select ENTRY_DATE, WISH_ITEM from WISH_LIST order by ENTRY_DATE");
            System.out.println(printLine);
            while (myWishes.next()) {
                System.out.println("On " + myWishes.getTimestamp(1) + " I wished for " + myWishes.getString(2));
            }
            System.out.println(printLine);
            //  Close the resultSet
            myWishes.close();

            // Release the resources (clean up )
            psInsert.close();
            s.close();
            conn.close();
            System.out.println("Closed connection");

            //   ## DATABASE SHUTDOWN SECTION ##
            if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
                boolean gotSQLExc = false;
                try {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                } catch (SQLException se) {
                    //通过XJ015判断，数据库是否正常关闭
                    if (se.getSQLState().equals("XJ015")) {
                        gotSQLExc = true;
                    }
                }
                if (!gotSQLExc) {
                    System.out.println("Database did not shut down normally");
                } else {
                    System.out.println("Database shut down normally");
                }
            }

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
        System.out.println("Getting Started With Derby JDBC program ending.");
    }


    public static boolean wwdChk4Table(Connection conTst) throws SQLException {
        boolean chk = true;
        boolean doCreate = false;
        try {
            Statement s = conTst.createStatement();
            s.execute("update WISH_LIST set ENTRY_DATE = CURRENT_TIMESTAMP, WISH_ITEM = 'TEST ENTRY' where 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            /** If table exists will get -  WARNING 02000: No row was found **/
            if (theError.equals("42X05"))   // Table does not exist
            {
                return false;
            } else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("WwdChk4Table: Incorrect table definition. Drop table WISH_LIST and rerun this program");
                throw sqle;
            } else {
                System.out.println("WwdChk4Table: Unhandled SQLException");
                throw sqle;
            }
        }
        //  System.out.println("Just got the warning - table exists OK ");
        return true;
    }
}
