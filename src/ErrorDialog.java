package src;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

public final class ErrorDialog extends JDialog {
   public ErrorDialog(String title, String content) {
      setTitle(title);
      setModal(true);

      setLayout(new FlowLayout());
      JLabel contentLabel = new JLabel(content);
      contentLabel.setForeground(new Color(156, 67, 11));
      contentLabel.setFont(new Font("Courier New", Font.BOLD, 17));

      add(contentLabel);
      pack();
   }
}
