package src.ModalDialog;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

//* Modal Dialog for displaying additional information
public final class ModalDialog extends JDialog {
   private ModalDialog(String title, String content, ColorCode code) {
      setTitle(title);
      setModal(true);

      setLayout(new FlowLayout());
      JLabel contentLabel = new JLabel(content);
      contentLabel.setForeground(code.color);
      contentLabel.setFont(new Font("Courier New", Font.BOLD, 17));

      add(contentLabel);
      pack();
   }

   //* Create and Show (Visibility) as a Popup
   public static void show(String title, String content, ColorCode code) {
      new ModalDialog(title, content, code).setVisible(true);
   }
}
