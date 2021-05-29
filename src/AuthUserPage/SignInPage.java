package src.AuthUserPage;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import src.Hashing;
import src.PostgresConn;
import src.ModalDialog.ColorCode;
import src.ModalDialog.ModalDialog;

public final class SignInPage extends AuthSignPage {
   public SignInPage(Runnable cleanupEvent) {
      super(cleanupEvent, true);
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      super.actionPerformed(event);

      PostgresConn.instance.with(stmt -> {
         ResultSet result = stmt.query("SELECT * FROM auth_user WHERE name = '%s'", nameField.getText());

         if (!result.next()) {
            nameField.setText("");
            passwordField.setText("");
            ModalDialog.show(
               "Invalid User Name", 
               "User Information not found. User does not exist",
               ColorCode.ERROR
            );
         } else {
            String password = result.getString("password");

            if (!Hashing.apply(passwordField.getPassword()).equals(password)) {
               passwordField.setText("");
               ModalDialog.show(
                  "Incorrect Password", 
                  "Passwords do not match",
                  ColorCode.ERROR
               );
            } else {
               nameField.setText("");
               passwordField.setText("");
               cleanupEvent.run();
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
