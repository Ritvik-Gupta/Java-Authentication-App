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

import src.UserAuthPage.PageName;
import src.UserAuthPage.ResetPasswordPage;
import src.UserAuthPage.SignInPage;
import src.UserAuthPage.SignUpPage;

public final class Main extends JFrame {
   private final JTabbedPane tabbedPane = new JTabbedPane();
   private final JButton backButton = new JButton();
   private final Runnable backEvent = () -> {
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
      backButton.addActionListener(event -> backEvent.run());

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      buildGui();
      pack();
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
      tabbedPane.addTab(PageName.SIGN_IN.toString(), new SignInPage(backEvent));
      tabbedPane.addTab(PageName.SIGN_UP.toString(), new SignUpPage(backEvent));
      tabbedPane.addTab(PageName.RESET_PASSWORD.toString(), new ResetPasswordPage(backEvent));

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
      new Main().setVisible(true);
   }
}
