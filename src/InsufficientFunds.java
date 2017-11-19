import javax.swing.JOptionPane;

public class InsufficientFunds extends Exception {

  public InsufficientFunds() {
    JOptionPane frame = new JOptionPane();
    JOptionPane.showMessageDialog(frame, "Sorry, you have insufficient funds.");
  }
}
