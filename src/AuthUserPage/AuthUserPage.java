package src.AuthUserPage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

//* Abstract Authentication User Page
//* Each Auth Page inherits and implements this
public abstract class AuthUserPage extends JPanel {
   protected final Runnable cleanupEvent;
   protected final JButton submitButton;

   public AuthUserPage(Runnable cleanupEvent, PageName pageName) {
      this.cleanupEvent = cleanupEvent;
      submitButton = new JButton(pageName.toString());
      //* Action Listener acts as a Submit Button Action 
      submitButton.addActionListener(event -> action());

      setLayout(null);
      setBorder(new TitledBorder(new EtchedBorder(), pageName.asForm()));

      add(submitButton);
   }

   //* Action Listener for the Submit Button
   public abstract void action();
}
