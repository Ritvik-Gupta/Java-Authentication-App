package src.AuthUserPage;

import java.util.ArrayList;

//* Static Members for each Authentication Page
public enum PageName {
   HOME_PAGE, SIGN_IN, SIGN_UP, RESET_PASSWORD;

   private final String value;

   private PageName() {
      //? Format and Reduce Name of enum for readability
      String[] nameTokens = name().split("_");
      ArrayList<String> valueTokens = new ArrayList<>();
      for (String name : nameTokens)
         valueTokens.add(name.charAt(0) + name.substring(1).toLowerCase());
      value = String.join(" ", valueTokens);
   }

   public String padded() {
      return " " + value + " ";
   }

   public String asForm() {
      return padded() + "Form ";
   }

   @Override
   public String toString() {
      return value;
   }
}
