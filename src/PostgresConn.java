package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class PostgresConn {
   @FunctionalInterface
   public static interface IStatementExec {
      void execute(Statement statement) throws SQLException;
   }

   private static final String CONN_URL = "postgresql://localhost:5432/";
   private static final String DB_NAME = "java-registration-system";
   private static final String USERNAME = "postgres";
   private static final String PASSWORD = "Ageofempire0207";

   public static final PostgresConn instance;
   static {
      PostgresConn conn = null;
      try {
         conn = new PostgresConn();
      } catch (ClassNotFoundException | SQLException err) {
         System.err.println("SQL Connection Error : " + err.getMessage());
         System.exit(0);
      }
      instance = conn;
   }

   private final Connection sqlConn;

   private PostgresConn() throws ClassNotFoundException, SQLException {
      Class.forName("org.postgresql.Driver");
      sqlConn = DriverManager.getConnection("jdbc:" + CONN_URL + DB_NAME, USERNAME, PASSWORD);
      System.out.println("\n\nConnected to < " + DB_NAME.toUpperCase() + " > successfully\n\n");
   }

   public void with(IStatementExec statementFn) {
      try {
         Statement statement = create();
         statementFn.execute(statement);
         statement.close();
      } catch (SQLException err) {
         System.err.println("SQL Access Error : " + err.getMessage());
         System.exit(0);
      }
   }

   public Statement create() throws SQLException {
      return sqlConn.createStatement();
   }
}
