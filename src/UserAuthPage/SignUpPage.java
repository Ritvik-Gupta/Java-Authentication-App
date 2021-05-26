package src.UserAuthPage;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import src.Hashing;
import src.PostgresConn;
import src.ModalDialog.ColorCode;
import src.ModalDialog.ModalDialog;

public final class SignUpPage extends AuthSignPage {
   public SignUpPage(Runnable cleanupEvent) {
      super(cleanupEvent, false);
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      super.actionPerformed(event);

      PostgresConn.instance.with(stmt -> {
         String name = nameField.getText();
         ResultSet result = stmt.query("SELECT COUNT(*) FROM auth_user WHERE name = '%s'", name);

         result.next();
         if (result.getInt("count") == 1) {
            nameField.setText("");
            passwordField.setText("");
            ModalDialog.show(
               "Invalid User Name", 
               "Name has already been registered by a User",
               ColorCode.ERROR
            );
         } else {
            stmt.mutation(
               "INSERT INTO auth_user VALUES ('%s', '%s')", 
               name, 
               Hashing.apply(passwordField.getPassword())
            );

            nameField.setText("");
            passwordField.setText("");
            cleanupEvent.run();
            ModalDialog.show(
               "Successfully Registered", 
               "Welcome to our application " + name.toUpperCase(),
               ColorCode.SUCCESS
            );
         }
      });
   }
}
