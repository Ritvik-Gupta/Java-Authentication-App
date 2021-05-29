package src.AuthUserPage;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public abstract class AuthSignPage extends AuthUserPage {
   protected final JLabel nameLabel;
   protected final JTextField nameField;
   protected final JLabel passwordLabel;
   protected final JPasswordField passwordField;

   public AuthSignPage(Runnable cleanupEvent, boolean isSignInPage) {
      super(cleanupEvent, isSignInPage ? PageName.SIGN_IN : PageName.SIGN_UP);
      submitButton.setBounds(550, 400, 150, 25);

      nameLabel = new JLabel("User Name :");
      nameLabel.setBounds(350, 130, 200, 20);
      nameField = new JTextField();
      nameField.setBounds(350, 150, 300, 30);

      passwordLabel = new JLabel("Password :");
      passwordLabel.setBounds(350, 230, 200, 20);
      passwordField = new JPasswordField();
      passwordField.setBounds(350, 250, 300, 30);

      add(nameLabel);
      add(nameField);
      add(passwordLabel);
      add(passwordField);
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      System.out.println();
      System.out.println("Name :\t" + nameField.getText());
      System.out.println("Password :\t" + new String(passwordField.getPassword()));
      System.out.println();
   }
}
