package com.bow.forest.hsqldb.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author vv
 * @since 2017/5/27.
 */
public class HsqlDBUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HsqlDBUtil.class);

    static {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Ready to close connection");
                    Connection con = getConnection();
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (HsqlDBUtil.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection("jdbc:hsqldb:file:hsql/ems;ifexists=true",
                                "SA", "");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return connection;
    }

    public static boolean execute(String sql) {
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            return stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql) {
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(AutoCloseable... closeable) {
        for (AutoCloseable c : closeable) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
