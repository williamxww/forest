package com.bow.forest.hsqldb.demo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vv
 * @since 2017/5/27.
 */
public class Demo1 {

    public static void printResult() {
        try {
            ResultSet rs = HsqlDBUtil.executeQuery("SELECT * FROM Test");
            int idResult;
            String textResult;
            while (rs.next()) {
                idResult = rs.getInt(1);
                textResult = rs.getString(2);
                System.out.println("ID: " + idResult + ", NAME: " + textResult);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void printCount(){
        try {
            ResultSet rs = HsqlDBUtil.executeQuery("SELECT count(1) FROM Test");
            int idResult;
            while (rs.next()) {
                idResult = rs.getInt(1);
                System.out.println("count: " + idResult);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void test() throws IOException {
        // HsqlDBUtil.execute("CREATE TABLE TEST(ID INT, NAME VARCHAR(100))");
        for (int i = 0; i < 10000; i++) {
            HsqlDBUtil.execute("INSERT INTO TEST(ID, NAME) VALUES((100,'HELLO'),(100,'HELLO'))");
            printCount();
//            printResult();
        }
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
