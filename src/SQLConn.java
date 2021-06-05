package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//* Application Singleton for SQL Connection (PostgreSQL in this case) and operations
//* Applies Late Initialization / Lazy Loading for startup performance
public final class SQLConn {
   //* PostgreSQL Connection Constants
   //* Update depending on your system configurations as well as SQL database used

   //? Using the PostgreSQL Driver
   private static final String DRIVER_NAME = "org.postgresql.Driver";
   //? Connection URL to local PSQL installed
   private static final String CONN_URL = "postgresql://localhost:5432/";
   //? Database created in PSQL
   private static final String DB_NAME = "java-registration-system";
   //? Username for PSQL
   private static final String USERNAME = "postgres";
   //? Password for PSQL
   private static final String PASSWORD = "Ageofempire0207";

   //* Stored Singleton Instance
   public static final SQLConn instance;
   static {
      SQLConn postgresConn = null;
      try {
         //? Create the Singleton Instance
         postgresConn = new SQLConn();
      } catch (ClassNotFoundException | SQLException err) {
         System.err.println("SQL Connection Error : " + err.getMessage());
         err.printStackTrace();
         //* Exit through the interface
         System.exit(0);
      }
      instance = postgresConn;
   }

   //* For Lamba Function execution with Optimized SQL Statement
   @FunctionalInterface
   public static interface IStatementExec {
      void execute(BasicStatement statement) throws SQLException;
   }

   //* Basic Statement that encapsulated 'java.sql.statement'
   //* Can AutoClose SQL statement being used after finishing the operation scope
   public final class BasicStatement implements AutoCloseable {
      private final Statement statement;

      public BasicStatement() throws SQLException {
         statement = sqlConn.createStatement();
      }

      //* Execute a SELECT or related query operation for the DB
      public ResultSet query(String sql, Object... params) throws SQLException {
         return statement.executeQuery(String.format(sql, params));
      }

      //* Execute a UPDATE or related mutation operation against the DB
      public void mutation(String sql, Object... params) throws SQLException {
         statement.executeUpdate(String.format(sql, params));
      }

      //* Runs Auto Close operation once out of scope
      @Override
      public void close() throws SQLException {
         statement.close();
      }
   }

   //? Stores the SQL Connection by Driver
   private final Connection sqlConn;

   private SQLConn() throws ClassNotFoundException, SQLException {
      //? Connect to the Driver being used
      Class.forName(DRIVER_NAME);
      //? Fetch the Connection to DB
      sqlConn = DriverManager.getConnection("jdbc:" + CONN_URL + DB_NAME, USERNAME, PASSWORD);
      System.out.println("\n\nConnected to < " + DB_NAME.toUpperCase() + " > successfully\n\n");
   }

   //* Scope Contained SQL Statement Function Operation
   public void with(IStatementExec statementFn) {
      //? Create a Auto Closing Basic Statement
      try(BasicStatement basicStmt = new BasicStatement()) {
         //? Execute the SQL statement specified
         statementFn.execute(basicStmt);
      } catch (SQLException err) {
         System.err.println("SQL Access Error : " + err.getMessage());
         err.printStackTrace();
         //* Exit through the interface
         System.exit(0);
      }
   }
}
