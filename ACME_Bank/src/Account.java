/*
 * Account.java
 *
 * Version:
 *  $Id: Account.java,v 1.3 2013/12/07 04:09:48 jxz5746 Exp $
 * Revisions:
 *  $Log: Account.java,v $
 *  Revision 1.3  2013/12/07 04:09:48  jxz5746
 *  Commented. Works.
 *
 *  Revision 1.2  2013/12/07 01:43:29  jxz5746
 *  Should be done. Needs Comments
 *
 *  Revision 1.1  2013/12/05 04:14:54  jxz5746
 *  Need to do some debuging and withdraw on ATM
 *  Needs Comments too
 *
 *
 *  Revision 1.0  2013/11/28 21:34:17  jxz5746
 *  Made Model, needs comments, what is format of bankFile?
 *
 */

/*
 * Bank Account Object
 * 
 * @author Jacob Zuber
 */

public interface Account 
{	
	/*
	 * Adds Interest to current account
	 * 
	 * return  Amount of interest that was added
	 */
	public double addInterest();
	
	/*
	 * Deducts Penalty
	 * 
	 * return  Amount deducted
	 */
	public double addPenalty();
	
	/*
	 * Checks if the account can be opened
	 * 
	 * return  true if account can be
	 *   opened, else false
	 */
	public boolean canOpen();
	
	/*
	 * returns String of what kind of account this is
	 * 
	 * return String representation of type of account
	 */
	public String getType();
	
	/*
	 * returns minimum balance the account
	 *  can be before monthly penalty is added
	 *  
	 * return minimum balance
	 */
	public double getMinBalance();
	
	/*
	 * returns Account's ID number
	 * 
	 * return  Account/ID Number
	 */
	public int getAccountNum();
	
	/*
	 * returns Account's Pin Number
	 * 
	 * return  Account's Pin Number
	 */
	public int getPin();
	
	/*
	 * returns current Balance of Account
	 * 
	 * return  Balance of the Account
	 */
	public double getBalance();
	
	/*
	 * Adds passed parameter to balance
	 * 
	 * @param  amt  Amount to be added to the balance
	 */
	public void Deposit(double amt);
	
	/*
	 * subtracts parameter from balance
	 * 
	 * @param  amt  Amount to be subtracted from the balance
	 * 
	 * @return  true if balance is greater than amt,
	 *            else false
	 */
	public boolean Withdraw(double amt);
}
