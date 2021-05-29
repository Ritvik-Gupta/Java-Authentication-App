package src.AuthUserPage;

public enum PageName {
   HOME("Home Page"), SIGN_IN("Sign In"), SIGN_UP("Sign Up"), RESET_PASSWORD("Reset Password");

   private final String value;

   private PageName(String value) {
      this.value = value;
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
