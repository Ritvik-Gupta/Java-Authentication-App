package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class PostgresConn {
   private static final String DRIVER_NAME = "org.postgresql.Driver";
   private static final String CONN_URL = "postgresql://localhost:5432/";
   private static final String DB_NAME = "java-registration-system";
   private static final String USERNAME = "postgres";
   private static final String PASSWORD = "Ageofempire0207";

   public static final PostgresConn instance;
   static {
      PostgresConn postgresConn = null;
      try {
         postgresConn = new PostgresConn();
      } catch (ClassNotFoundException | SQLException err) {
         System.err.println("SQL Connection Error : " + err.getMessage());
         err.printStackTrace();
         System.exit(0);
      }
      instance = postgresConn;
   }

   @FunctionalInterface
   public static interface IStatementExec {
      void execute(BasicStatement statement) throws SQLException;
   }

   public final class BasicStatement {
      private final Statement statement;

      public BasicStatement() throws SQLException {
         statement = sqlConn.createStatement();
      }

      public ResultSet query(String sql, Object... params) throws SQLException {
         return statement.executeQuery(String.format(sql, params));
      }

      public void mutation(String sql, Object... params) throws SQLException {
         statement.executeUpdate(String.format(sql, params));
      }

      public void close() throws SQLException {
         statement.close();
      }
   }

   private final Connection sqlConn;

   private PostgresConn() throws ClassNotFoundException, SQLException {
      Class.forName(DRIVER_NAME);
      sqlConn = DriverManager.getConnection("jdbc:" + CONN_URL + DB_NAME, USERNAME, PASSWORD);
      System.out.println("\n\nConnected to < " + DB_NAME.toUpperCase() + " > successfully\n\n");
   }

   public void with(IStatementExec statementFn) {
      try {
         BasicStatement basicStmt = new BasicStatement();
         statementFn.execute(basicStmt);
         basicStmt.close();
      } catch (SQLException err) {
         System.err.println("SQL Access Error : " + err.getMessage());
         System.exit(0);
      }
   }
}
