/*
 * CD.java
 *
 * Version:
 *  $Id: CD.java,v 1.3 2013/12/07 04:09:48 jxz5746 Exp $
 * Revisions:
 *  $Log: CD.java,v $
 *  Revision 1.3  2013/12/07 04:09:48  jxz5746
 *  Commented. Works.
 *
 *  Revision 1.2  2013/12/07 01:43:29  jxz5746
 *  Should be done. Needs Comments
 *
 *  Revision 1.1  2013/12/05 04:14:55  jxz5746
 *  Need to do some debuging and withdraw on ATM
 *  Needs Comments too
 *
 *
 *  Revision 1.0  2013/11/28 21:34:17  jxz5746
 *  Made Model, needs comments, what is format of bankFile?
 *
 */

/*
 * Type of Bank Account called CD
 * 
 * @author Jacob Zuber
 */

public class CD implements Account 
{
	private double minBal = 500;
	private int AccountNum;
	private int pin;
	private double bal;
	
	/*
	 * Constructor
	 *  Creates CD Account
	 *  
	 * @param  AccountNum  Account's Account/ID Number
	 * @param  pin  Account's PIN Number
	 * @param  bal  Account's balance
	 */
	public CD(int AccountNum, int pin, double bal)
	{
		this.AccountNum = AccountNum;
		this.pin = pin;
		this.bal = bal;
	}
	
	@Override
	/*
	 * Adds passed parameter to balance
	 * 
	 * @param  amt  Amount to be added to the balance
	 */
	public void Deposit(double amt)
	{
		this.bal += amt;
	}
	
	@Override
	/*
	 * subtracts parameter from balance
	 * 
	 * @param  amt  Amount to be subtracted from the balance
	 * 
	 * @return  true if balance is greater than amt,
	 *            else false
	 */
	public boolean Withdraw(double amt)
	{
		return false;
	}
	
	@Override
	/*
	 * Adds Interest to current account
	 * 
	 * return  Amount of interest that was added
	 */
	public double addInterest() {
		String ammount = "" + bal*(.05/12);
		int decimalNum = ammount.indexOf(".");
		double interest = 0;
		if(decimalNum != -1)
		{
			ammount = ammount.substring(0, decimalNum+3);
			interest = Double.parseDouble(ammount);
		}
		else
		{
			interest = bal*(.05/12);
		}
		
		bal += interest;
		return interest;
	}

	@Override
	/*
	 * Deducts Penalty
	 * 
	 * return  Amount deducted
	 */
	public double addPenalty() {
		return 0;
	}

	@Override
	/*
	 * Checks if the account can be opened
	 * 
	 * return  true if account can be
	 *   opened, else false
	 */
	public boolean canOpen() {
		if(this.bal >= this.minBal)
			return true;
		else
			return false;
	}

	@Override
	/*
	 * returns String of what kind of account this is
	 * 
	 * return String representation of type of account
	 */
	public String getType() {
		return "CD";
	}

	@Override
	/*
	 * returns minimum balance the account
	 *  can be before monthly penalty is added
	 *  
	 * return minimum balance
	 */
	public double getMinBalance() {
		return minBal;
	}

	@Override
	/*
	 * returns Account's ID number
	 * 
	 * return  Account/ID Number
	 */
	public int getAccountNum() {
		return AccountNum;
	}

	@Override
	/*
	 * returns Account's Pin Number
	 * 
	 * return  Account's Pin Number
	 */
	public int getPin() {
		return pin;
	}

	@Override
	/*
	 * returns current Balance of Account
	 * 
	 * return  Balance of the Account
	 */
	public double getBalance() {
		return bal;
	}

}
