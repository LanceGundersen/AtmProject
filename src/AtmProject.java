import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * AtmProject.java Lance Gundersen 19NOV17
 *
 * @version 1.0
 *
 * This program implements an ATM machine. You can withdrawFunds, depositFunds and transfer money
 * into a checking or savings account. You can also check the balance of either account.
 *
 * The program initializes with a starting value of $500 in checking.
 *
 * The program implements error checking in and presents it to the user based on a dialog.
 */

public class AtmProject extends JFrame {

  private static final int WINDOWWIDTH = 350, WINDOWHEIGHT = 200,
      TEXTWIDTH = 225, TEXTHEIGHT = 25;
  private static Account checking = new Account().new Checking();
  private static Account savings = new Account().new Savings();
  private static DecimalFormat decimalFormat = new DecimalFormat("$0.00");
  private JRadioButton checkingRadioButton = new JRadioButton("Checking");
  private JRadioButton savingsRadioButton = new JRadioButton("Savings");
  private JTextField amountEntry = new JTextField("");
  private JOptionPane messagePane = new JOptionPane();

  /**
   * Constructor
   *
   * A constructor that initializes the panels. It then initializes accounts sets up the action
   * listeners to each of the buttons.
   */
  public AtmProject(double checkingInitialBalance, double savingsInitialBalance) {

    super("The ATM Machine");
    setLayout(new GridBagLayout());
    GridBagConstraints layout = new GridBagConstraints();
    setMainFrame(WINDOWWIDTH, WINDOWHEIGHT);
    JPanel buttonPanel = new JPanel();
    JPanel amountEntry = new JPanel();
    setResizable(false);
    layout.gridy = 2;
    add(buttonPanel);
    add(amountEntry, layout);
    buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
    amountEntry.setLayout(new GridLayout(1, 1));
    JButton withdrawButton = new JButton("Withdraw");
    buttonPanel.add(withdrawButton);
    JButton depositButton = new JButton("Deposit");
    buttonPanel.add(depositButton);
    JButton transferToButton = new JButton("Transfer To");
    buttonPanel.add(transferToButton);
    JButton balanceButton = new JButton("Balance");
    buttonPanel.add(balanceButton);
    ButtonGroup radios = new ButtonGroup();
    radios.add(checkingRadioButton);
    radios.add(savingsRadioButton);
    buttonPanel.add(checkingRadioButton);
    buttonPanel.add(savingsRadioButton);
    this.amountEntry.setPreferredSize(new Dimension(TEXTWIDTH, TEXTHEIGHT));
    checkingRadioButton.setSelected(true);
    amountEntry.add(this.amountEntry);
    initializeAccounts(checkingInitialBalance, savingsInitialBalance);
    withdrawButton.addActionListener(new WithdrawButtonListener());
    depositButton.addActionListener(new DepositButtonListener());
    transferToButton.addActionListener(new TransferToButtonListener());
    balanceButton.addActionListener(new BalanceButtonListener());
  }

  public static void initializeAccounts(double checkingStartingBalance,
      double savingsStartingBalance) {
    checking.setAccountBalance(checkingStartingBalance);
    savings.setAccountBalance(savingsStartingBalance);
  }

  public static void main(String[] args) {
    AtmProject messagePane = new AtmProject(500, 0);
    messagePane.display();
  }

  public void errorValidNumber() {
    JOptionPane.showMessageDialog(messagePane, "Sorry, please enter a valid amount! " +
        "If you are trying to withdraw, use $20 amounts.");
  }

  // Methods
  public double getAmountValue() {
    try {
      return Double.parseDouble(amountEntry.getText());
    } catch (NumberFormatException e) {
      clearAmountValue();
      return 0;
    }
  }

  public void clearAmountValue() {
    amountEntry.setText("");
  }

  private void setMainFrame(int width, int height) {
    setSize(width, height);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void display() {
    setVisible(true);
  }

  // Action Listeners
  class WithdrawButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        if (getAmountValue() > 0 && getAmountValue() % 20 == 0) {
          // Checks radio selection
          if (checkingRadioButton.isSelected()) {
            checking.withdrawFunds(getAmountValue());
            JOptionPane.showMessageDialog(messagePane, decimalFormat.format(getAmountValue()) +
                " withdrawn from Checking account successfully.");
          } else if (savingsRadioButton.isSelected()) {
            savings.withdrawFunds(getAmountValue());
            JOptionPane.showMessageDialog(messagePane, decimalFormat.format(getAmountValue()) +
                " withdrawn from Savings account successfully.");
          }
          clearAmountValue();
        } else {
          errorValidNumber();
        }
        clearAmountValue();
      } catch (InsufficientFunds insufficientFunds) { }
    }
  }

  class DepositButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (getAmountValue() > 0) {
        if (checkingRadioButton.isSelected()) {
          checking.depositFunds(getAmountValue());
          JOptionPane.showMessageDialog(messagePane, decimalFormat.format(getAmountValue()) +
              " deposited into Checking account successfully.");
        } else if (savingsRadioButton.isSelected()) {
          savings.depositFunds(getAmountValue());
          JOptionPane.showMessageDialog(messagePane, decimalFormat.format(getAmountValue()) +
              " deposited into Savings account successfully.");
        }
        clearAmountValue();
      } else {
        errorValidNumber();
      }
      clearAmountValue();
    }
  }

  class TransferToButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        if (getAmountValue() > 0) {
          if (checkingRadioButton.isSelected()) {
            savings.transferFromAccount(getAmountValue());
            checking.transferToAccount(getAmountValue());
            JOptionPane.showMessageDialog(messagePane, decimalFormat.format(getAmountValue()) +
                " transferred from Savings account to Checking account successfully.");
          } else if (savingsRadioButton.isSelected()) {
            checking.transferFromAccount(getAmountValue());
            savings.transferToAccount(getAmountValue());
            JOptionPane.showMessageDialog(messagePane, decimalFormat.format(getAmountValue()) +
                " transferred from Checking account to Savings account successfully.");
          }
          clearAmountValue();
        } else {
          errorValidNumber();
        }
        clearAmountValue();
      } catch (InsufficientFunds insufficientFunds) { }
    }
  }

  class BalanceButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (checkingRadioButton.isSelected()) {
        JOptionPane.showMessageDialog(messagePane,
            "Checking account balance is: \n" +
                decimalFormat.format(checking.getAccountBalance()));
      } else if (savingsRadioButton.isSelected()) {
        JOptionPane.showMessageDialog(messagePane,
            "Savings account balance is: \n" +
                decimalFormat.format(savings.getAccountBalance()));
      } else {
        errorValidNumber();
      }
      clearAmountValue();
    }
  }

}
