package src.UserAuthPage;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import src.Hashing;
import src.PostgresConn;
import src.ModalDialog.ColorCode;
import src.ModalDialog.ModalDialog;

public final class ResetPasswordPage extends UserAuthPage {
   private String otp = null;
   private final JButton generateOtpButton;
   private final JLabel nameLabel;
   private final JTextField nameField;
   private final JLabel otpLabel;
   private final JTextField otpField;
   private final JLabel passwordLabel;
   private final JPasswordField passwordField;

   public ResetPasswordPage(Runnable cleanupEvent) {
      super(cleanupEvent, PageName.RESET_PASSWORD);
      submitButton.setBounds(550, 450, 150, 25);
      

      generateOtpButton=new JButton("Generate OTP");
      generateOtpButton.setBounds(250, 450, 150, 25);
      generateOtpButton.addActionListener(event -> {
         otp = generateOtp();
         ModalDialog.show(
            "One Time Password ( OTP )", 
            "Verification OTP is : " + otp,
            ColorCode.NEUTRAL
         );
      });

      nameLabel = new JLabel("User Name :");
      nameLabel.setBounds(350, 100, 200, 20);
      nameField = new JTextField();
      nameField.setBounds(350, 120, 300, 30);
      
      otpLabel = new JLabel("Verify OTP :");
      otpLabel.setBounds(350, 200, 200, 20);
      otpField = new JTextField();
      otpField.setBounds(350, 220, 300, 30);

      passwordLabel = new JLabel("Password :");
      passwordLabel.setBounds(350, 300, 200, 20);
      passwordField = new JPasswordField();
      passwordField.setBounds(350, 320, 300, 30);

      add(generateOtpButton);
      add(nameLabel);
      add(nameField);
      add(otpLabel);
      add(otpField);
      add(passwordLabel);
      add(passwordField);
   }

   private void clear() {
      nameField.setText("");
      otpField.setText("");
      passwordField.setText("");
      otp = null;
   }

   private static String generateOtp() {
      Random randomGen = new Random();
      IntStream otpStream = randomGen.ints(6, 0, 10);
      return otpStream.mapToObj(Integer::toString).reduce("", String::concat);
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      PostgresConn.instance.with(stmt -> {
         String name = nameField.getText();
         ResultSet result = stmt.query("SELECT COUNT(*) FROM auth_user WHERE name = '%s'", name);

         result.next();
         if (result.getInt("count") == 0) {
            clear();
            cleanupEvent.run();
            ModalDialog.show(
               "Invalid User Name", 
               "User Information not found. User does not exist",
               ColorCode.ERROR
            );
         } else {
            if(!otpField.getText().equals(otp)) {
               clear();
               cleanupEvent.run();
               ModalDialog.show(
                  "Incorrect OTP specified", 
                  "OTP entered does not match the actual OTP",
                  ColorCode.ERROR
               );
            } else {
               stmt.mutation(
                  "UPDATE auth_user SET password = '%s' WHERE name = '%s'",
                  Hashing.apply(passwordField.getPassword()),
                  name
               );

               clear();
               cleanupEvent.run();
               ModalDialog.show(
                  "Successfully Updated the Password", 
                  "Password has been Updated successfully " + name.toUpperCase(),
                  ColorCode.SUCCESS
               );
            }
         }
      });
   }
}
