/**  
 * @FileName: ConnectionTransaction.java 
 * @Package common.db 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package common.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * @ClassName: ConnectionTransaction 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年6月15日 下午10:09:37  
 */

public class ConnectionTransaction {
    public static void main(String[] args) {
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:eiis", "EIIS", "eiis");
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            int count = statement
                    .executeUpdate("insert into hill_organizatin(id,pid,description) values('5', '0','testCon')");
            System.out.println(count);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
