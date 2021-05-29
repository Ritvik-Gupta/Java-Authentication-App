package src.AuthUserPage;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public abstract class AuthUserPage extends JPanel implements ActionListener {
   protected final Runnable cleanupEvent;
   protected final JButton submitButton;

   public AuthUserPage(Runnable cleanupEvent, PageName pageName) {
      this.cleanupEvent = cleanupEvent;

      submitButton = new JButton(pageName.toString());
      submitButton.addActionListener(this);

      setLayout(null);
      setBorder(new TitledBorder(new EtchedBorder(), pageName.asForm()));

      add(submitButton);
   }
}
