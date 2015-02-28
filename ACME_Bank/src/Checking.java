/*
 * Checking.java
 *
 * Version:
 *  $Id: Checking.java,v 1.3 2013/12/07 04:09:48 jxz5746 Exp $
 * Revisions:
 *  $Log: Checking.java,v $
 *  Revision 1.3  2013/12/07 04:09:48  jxz5746
 *  Commented. Works.
 *
 *  Revision 1.2  2013/12/07 01:43:29  jxz5746
 *  Should be done. Needs Comments
 *
 *  Revision 1.1  2013/12/05 04:14:56  jxz5746
 *  Need to do some debuging and withdraw on ATM
 *  Needs Comments too
 *
 *
 *  Revision 1.0  2013/11/28 21:34:17  jxz5746
 *  Made Model, needs comments, what is format of bankFile?
 *
 */

/*
 * Type of Bank Account called Checking
 * 
 * @author Jacob Zuber
 */

public class Checking implements Account
{
	
	public final double minBal = 50;
	public final int AccountNum;
	public final int pin;
	private double bal;
	
	/*
	 * Constructor
	 *  Creates Checking Account
	 *  
	 * @param  AccountNum  Account's Account/ID Number
	 * @param  pin  Account's PIN Number
	 * @param  bal  Account's balance
	 */
	public Checking(int AccountNum, int pin, double bal)
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
		if(this.bal - amt < 0)
		{
			return false;
		}
		else
		{
			this.bal -= amt;
			return true;
		}
	}

	@Override
	/*
	 * Adds Interest to current account
	 * 
	 * return  Amount of interest that was added
	 */
	public double addInterest() {
		return 0;
	}

	@Override
	/*
	 * Deducts Penalty
	 * 
	 * return  Amount deducted
	 */
	public double addPenalty() {
		if(bal < minBal)
		{
			if(bal > 5)
			{
				this.bal -= 5;
				return 5;
			}
			else
			{
				String ammount = ""+ this.bal*.05;
				int decimalNum = ammount.indexOf(".");
				double penalty = 0;
				if(decimalNum != -1)
				{
					ammount = ammount.substring(0, decimalNum+3);
					penalty = Double.parseDouble(ammount);
				}
				else
				{
					penalty = this.bal*.05;
				}
				
				this.bal -= penalty;
				return penalty;
			}
		}
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
		return true;
	}

	@Override
	/*
	 * returns String of what kind of account this is
	 * 
	 * return String representation of type of account
	 */
	public String getType() {
		return "Checking";
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
