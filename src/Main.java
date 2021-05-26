package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public final class Main extends JFrame {
   private final JTabbedPane tabbedPane = new JTabbedPane();
   private final JButton backButton = new JButton();
   private final ActionListener backAndSubmitEvent = event -> {
      tabbedPane.setSelectedIndex(0);
      backButton.setEnabled(false);
   };

   private final class HomeButton extends JPanel {
      public HomeButton(int tabbedPaneIndex, PageName pageName) {
         JButton button = new JButton(pageName.toString());
         setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
         button.addActionListener(event -> {
            tabbedPane.setSelectedIndex(tabbedPaneIndex);
            backButton.setEnabled(true);
         });
         add(button, BorderLayout.CENTER);
      }
   }

   public Main() {
      tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      tabbedPane.setEnabled(false);

      backButton.setText("Go Back");
      backButton.setEnabled(false);
      backButton.addActionListener(backAndSubmitEvent);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      buildGui();
      pack();
   }

   private JPanel createSignInPage() {
      JPanel signInPage = new JPanel();
      signInPage.setLayout(null);
      signInPage.setBorder(new TitledBorder(new EtchedBorder(), PageName.SIGN_IN.asForm()));

      JLabel nameLabel = new JLabel("User Name :");
      nameLabel.setBounds(350, 130, 200, 20);
      JTextField nameField = new JTextField();
      nameField.setBounds(350, 150, 300, 30);

      JLabel passwordLabel = new JLabel("Password :");
      passwordLabel.setBounds(350, 230, 200, 20);
      JPasswordField passwordField = new JPasswordField();
      passwordField.setBounds(350, 250, 300, 30);

      JButton submitButton = new JButton(PageName.SIGN_IN.toString());
      submitButton.setBounds(550, 400, 200, 25);
      submitButton.addActionListener(event -> {
         System.out.println("Name :\t" + nameField.getText());
         System.out.println("Password :\t" + new String(passwordField.getPassword()));
         nameField.setText("");
         passwordField.setText("");
         backAndSubmitEvent.actionPerformed(event);
      });

      signInPage.add(nameLabel);
      signInPage.add(nameField);
      signInPage.add(passwordLabel);
      signInPage.add(passwordField);
      signInPage.add(submitButton);

      return signInPage;
   }

   private JPanel createSignUpPage() {
      JPanel signUpPage = new JPanel();
      signUpPage.setLayout(null);
      signUpPage.setBorder(new TitledBorder(new EtchedBorder(), PageName.SIGN_UP.asForm()));

      JLabel nameLabel = new JLabel("What is your User Name ?");
      nameLabel.setBounds(350, 130, 200, 20);
      JTextField nameField = new JTextField();
      nameField.setBounds(350, 150, 300, 30);

      JLabel passwordLabel = new JLabel("Enter a Password :");
      passwordLabel.setBounds(350, 230, 200, 20);
      JPasswordField passwordField = new JPasswordField();
      passwordField.setBounds(350, 250, 300, 30);

      JButton submitButton = new JButton(PageName.SIGN_UP.toString());
      submitButton.setBounds(550, 400, 200, 25);
      submitButton.addActionListener(event -> {
         System.out.println("Name :\t" + nameField.getText());
         System.out.println("Password :\t" + new String(passwordField.getPassword()));

         PostgresConn.instance.with(stmt -> {
            ResultSet result = stmt.executeQuery(
               String.format(
                  "SELECT COUNT(*) FROM auth_user WHERE name = '%s'", 
                  nameField.getText()
               )
            );
            result.next();
            if (result.getInt("count") == 1)
               new ErrorDialog("Invalid User Name", "Name has been registered by a User").setVisible(true);
            else {
               nameField.setText("");
               passwordField.setText("");
               backAndSubmitEvent.actionPerformed(event);
            }
         });
      });

      signUpPage.add(nameLabel);
      signUpPage.add(nameField);
      signUpPage.add(passwordLabel);
      signUpPage.add(passwordField);
      signUpPage.add(submitButton);

      return signUpPage;
   }

   private void buildGui() {
      JPanel homePage = new JPanel();
      homePage.setLayout(new BorderLayout());
      homePage.setBorder(new TitledBorder(new EtchedBorder(), PageName.HOME.padded()));

      HomeButton signInButton = new HomeButton(1, PageName.SIGN_IN);
      homePage.add(signInButton, BorderLayout.NORTH);

      HomeButton signUpButton = new HomeButton(2, PageName.SIGN_UP);
      homePage.add(signUpButton, BorderLayout.CENTER);

      HomeButton resetPasswordButton = new HomeButton(3, PageName.RESET_PASSWORD);
      homePage.add(resetPasswordButton, BorderLayout.SOUTH);

      tabbedPane.addTab(PageName.HOME.toString(), homePage);
      tabbedPane.addTab(PageName.SIGN_IN.toString(), createSignInPage());
      tabbedPane.addTab(PageName.SIGN_UP.toString(), createSignUpPage());

      add(tabbedPane, BorderLayout.CENTER);

      JPanel bottomPanel = new JPanel();
      bottomPanel.setLayout(new BorderLayout());
      bottomPanel.setBorder(new TitledBorder(new EtchedBorder()));
      bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      bottomPanel.add(backButton, BorderLayout.EAST);
      add(bottomPanel, BorderLayout.SOUTH);

      setPreferredSize(new Dimension(1000, 600));
   }

   public static void main(String[] args) {
      PostgresConn.instance.with(stmt -> {});
      new Main().setVisible(true);
   }
}
