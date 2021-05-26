package src.ModalDialog;

import java.awt.Color;

public enum ColorCode {
   SUCCESS(new Color(34, 139, 34)), NEUTRAL(new Color(112, 128, 144)), ERROR(new Color(220, 20, 60));

   public final Color color;

   private ColorCode(Color color) {
      this.color = color;
   }
}
