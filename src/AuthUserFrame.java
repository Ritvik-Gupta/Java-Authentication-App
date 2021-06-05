package src;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import src.AuthUserPage.PageName;
import src.AuthUserPage.ResetPasswordPage;
import src.AuthUserPage.SignInPage;
import src.AuthUserPage.SignUpPage;


//* Main Frame Page for the Application
//* Using Java Swing API (JFrame)
public final class AuthUserFrame extends JFrame {
   //? A Multi Tab Pane for the Frame
   private final JTabbedPane tabbedPane = new JTabbedPane();
   //? Common Utility Back Button for the Navbar 
   private final JButton backButton = new JButton();
   //? Event Handler for when Back Button is Clicked
   private final Runnable backEvent = () -> {
      //* Sets the application back to deafult state
      tabbedPane.setSelectedIndex(0);
      backButton.setEnabled(false);
   };

   //* Specific Home Button for Sign In, Sign Up and Reset Password Pages
   private final class HomeButton extends JPanel {
      public HomeButton(int tabbedPaneIndex, PageName pageName) {
         //? Call Button to go to the pane
         JButton button = new JButton(pageName.toString());
         setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
         button.addActionListener(event -> {
            //* Updates the application pane to the selected tab
            tabbedPane.setSelectedIndex(tabbedPaneIndex);
            backButton.setEnabled(true);
         });
         add(button, BorderLayout.CENTER);
      }
   }

   public AuthUserFrame() {
      //* Application Name
      setName("Salamander's Authentication Interface");

      tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      //* Cannot change tabs directly 
      tabbedPane.setEnabled(false);

      backButton.setText("Go Back");
      //* When on the Home Page back button is disabled
      backButton.setEnabled(false);
      backButton.addActionListener(event -> backEvent.run());

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      buildGui();
      pack();
   }

   //* For Building the actual GUI of the application with all the Utilities
   private void buildGui() {
      //? Home Page Panel that stores all the Home Buttons
      JPanel homePage = new JPanel();
      homePage.setLayout(new BorderLayout());
      homePage.setBorder(new TitledBorder(new EtchedBorder(), PageName.HOME_PAGE.padded()));

      //? For Sign In Page
      HomeButton signInButton = new HomeButton(1, PageName.SIGN_IN);
      homePage.add(signInButton, BorderLayout.NORTH);

      //? For Sign Up Page
      HomeButton signUpButton = new HomeButton(2, PageName.SIGN_UP);
      homePage.add(signUpButton, BorderLayout.CENTER);

      //? For Reset Password Page
      HomeButton resetPasswordButton = new HomeButton(3, PageName.RESET_PASSWORD);
      homePage.add(resetPasswordButton, BorderLayout.SOUTH);

      //* Setup the Panes for the Tabs of the application
      //* First Page (index 0) is the Home Page
      tabbedPane.addTab(PageName.HOME_PAGE.toString(), homePage);
      tabbedPane.addTab(PageName.SIGN_IN.toString(), new SignInPage(backEvent));
      tabbedPane.addTab(PageName.SIGN_UP.toString(), new SignUpPage(backEvent));
      tabbedPane.addTab(PageName.RESET_PASSWORD.toString(), new ResetPasswordPage(backEvent));

      add(tabbedPane, BorderLayout.CENTER);

      //* Create the Navbar Bottom Panel for the application 
      //* with just the Back Button
      JPanel bottomPanel = new JPanel();
      bottomPanel.setLayout(new BorderLayout());
      bottomPanel.setBorder(new TitledBorder(new EtchedBorder()));
      bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      bottomPanel.add(backButton, BorderLayout.EAST);
      add(bottomPanel, BorderLayout.SOUTH);

      //? Screen Preferred Width
      setPreferredSize(new Dimension(1000, 600));
   }
}
