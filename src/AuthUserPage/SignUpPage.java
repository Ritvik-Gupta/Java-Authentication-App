package src.AuthUserPage;

import java.sql.ResultSet;

import src.Hashing;
import src.SQLConn;
import src.ModalDialog.ColorCode;
import src.ModalDialog.ModalDialog;

public final class SignUpPage extends AuthSignPage {
   public SignUpPage(Runnable cleanupEvent) {
      super(cleanupEvent, false);
   }

   @Override
   public void action() {
      super.action();

      //* Create a SQL Statement Closure
      SQLConn.instance.with(stmt -> {
         String name = nameField.getText();
         //? Select number of Auth-Users with a specific name in the DB
         //? Name of User is a primary key so there is 1 or no Auth-User with the specified name
         ResultSet result = stmt.query("SELECT COUNT(*) FROM auth_user WHERE name = '%s'", name);

         result.next();
         //? If there already is a Auth-User with the specified name
         if (result.getInt("count") == 1) {
            nameField.setText("");
            passwordField.setText("");
            //* Show an Error Modal Dialog
            ModalDialog.show(
               "Invalid User Name", 
               "Name has already been registered by a User",
               ColorCode.ERROR
            );
         } else {
            //? Insert a new Auth-User with a specified fields in the DB
            stmt.mutation(
               "INSERT INTO auth_user VALUES ('%s', '%s')", 
               name, 
               //? Insert only the Hashed Password for security
               Hashing.apply(passwordField.getPassword())
            );

            nameField.setText("");
            passwordField.setText("");
            //* Submit and Clean up
            cleanupEvent.run();
            //* Show a Success Modal Dialog
            ModalDialog.show(
               "Successfully Registered", 
               "Welcome to our application " + name.toUpperCase(),
               ColorCode.SUCCESS
            );
         }
      });
   }
}
