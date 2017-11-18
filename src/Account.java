/**
 * This class has multiple constructors, sub-classes and methods for the program.
 *
 * The variable balance is utilized by each method so that each account has it's own balance.
 * If a method requires it, it can throw the InsuffientFunds dialog so the user is aware of
 * a balance deficiency.
 */

public class Account {

  private double balance;

  public Account() {
  }

  public double getAccountBalance() {
    return this.balance;
  }

  public void setAccountBalance(double balance) {
    this.balance = balance;
  }

  public void withdrawFunds(double withdrawAmount) throws InsufficientFunds {
    if (this.balance - withdrawAmount < 0) {
      throw new InsufficientFunds();
    }
    this.balance = this.balance - withdrawAmount;
  }

  public void depositFunds(double amount) {
    this.balance = this.balance + amount;
  }

  public void transferToAccount(double amount) {
    this.balance = this.balance + amount;
  }

  public void transferFromAccount(double amount) throws InsufficientFunds {
    if (this.balance - amount < 0) {
      throw new InsufficientFunds();
    }
    this.balance = this.balance - amount;
  }

  public class Checking extends Account {

    public Checking() {
    }
  }

  public class Savings extends Account {

    public Savings() {
    }
  }
}
