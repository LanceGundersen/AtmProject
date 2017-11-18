import javax.swing.JOptionPane;

/**
 * This class has multiple constructors, sub-classes and methods for the program.
 *
 * The variable balance is utilized by each method so that each account has it's own balance.
 * If a method requires it, it can throw the InsuffientFunds dialog so the user is aware of
 * a balance deficiency.
 */

public class InsufficientFunds extends Exception {

  public InsufficientFunds() {
    JOptionPane frame = new JOptionPane();
    JOptionPane.showMessageDialog(frame, "Sorry, you have insufficient funds.");
  }
}
