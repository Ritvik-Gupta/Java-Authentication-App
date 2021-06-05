package src.AuthUserPage;

import java.sql.ResultSet;

import src.Hashing;
import src.SQLConn;
import src.ModalDialog.ColorCode;
import src.ModalDialog.ModalDialog;

public final class SignInPage extends AuthSignPage {
   public SignInPage(Runnable cleanupEvent) {
      super(cleanupEvent, true);
   }

   @Override
   public void action() {
      super.action();

      //* Create a SQL Statement Closure
      SQLConn.instance.with(stmt -> {
         //? Select every Auth-User with a specific name in the DB
         //? Name of User is a primary key so there is 1 or no Auth-User with the specified name
         ResultSet result = stmt.query("SELECT * FROM auth_user WHERE name = '%s'", nameField.getText());

         //? If there are no Auth-Users with the specified name
         if (!result.next()) {
            nameField.setText("");
            passwordField.setText("");
            //* Show an Error Modal Dialog
            ModalDialog.show(
               "Invalid User Name", 
               "User Information not found. User does not exist",
               ColorCode.ERROR
            );
         } else {
            //? Get the hashed password stored in the DB 
            String password = result.getString("password");

            //? Fetch the password entered by the User 
            //? Hash it and compare it to the actual stored hash password 
            if (!Hashing.apply(passwordField.getPassword()).equals(password)) {
               //* Ask for User to type the password again
               passwordField.setText("");
               //* Show an Error Modal Dialog
               ModalDialog.show(
                  "Incorrect Password", 
                  "Passwords do not match",
                  ColorCode.ERROR
               );
            } else {
               nameField.setText("");
               passwordField.setText("");
               //* Submit and Clean up
               cleanupEvent.run();
               //* Show a Success Modal Dialog
               ModalDialog.show(
                  "Welcome home", 
                  "Good to have you back " + result.getString("name").toUpperCase(),
                  ColorCode.SUCCESS
               );
            }
         }
      });
   }
}
