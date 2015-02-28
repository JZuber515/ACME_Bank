/*
 * ATM.java
 *
 * Version:
 *  $Id: ATM.java,v 1.3 2013/12/07 04:09:49 jxz5746 Exp $
 * Revisions:
 *  $Log: ATM.java,v $
 *  Revision 1.3  2013/12/07 04:09:49  jxz5746
 *  Commented. Works.
 *
 *  Revision 1.2  2013/12/06 20:18:08  jxz5746
 *  Need to do batchFile and Comments
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
 * 
 * @author Jacob Zuber
 */

import java.util.*;

public class ATM extends Observable
{
	private Bank bank;
	ArrayList<Account> accounts;
	private int stepNum;
	private int transStepNum;
	private String prompt;
	private Account currentAccount;
	
	private boolean successfulDeposit;
	private boolean successfulWithdraw;
	
	/*
	 * Constructor
	 * 	Creates an ATM connected to a Bank
	 */
	public ATM(Bank bank)
	{
		this.bank = bank;
		this.accounts = bank.getAccounts();
		currentAccount = null;
		stepNum = 0;
		transStepNum = -1;
		setPrompt();
	}
	
	/*
	 * Check if passed Account's Account
	 *  number matches passed number
	 *  
	 * @param  a  Account to check number with
	 * @param  num  number to check with Account
	 * 
	 * @return  true if num matches a's Account Number
	 */
	public boolean isValidAccountNumber(Account a, int num)
	{
		if(a.getAccountNum() == num)
		{
			return true;
		}
		else
			return false;
	}
	
	/*
	 * Check if passed Account's PIN 
	 *  number matches passed number
	 *  
	 * @param  a  Account to check number
	 * @param  num  number to check with Account
	 * 
	 * @return  true of num matches a's PIN Number
	 */
	public boolean isValidPIN(Account a, int num)
	{
		if(a.getPin() == num)
			return true;
		else
			return false;
	}
	
	/*
	 * Withdraws passed amount from passed account
	 * 
	 * @param  a  Account to withdraw from
	 * @param  ammount  Amount to withdraw from Account
	 * 
	 * @return  true if successful withdraw
	 */
	public boolean Withdraw(Account a, double ammount)
	{
		if(bank.Withdraw(a, ammount))
		{
			successfulWithdraw = true;
			return true;
		}
		else
		{
			successfulWithdraw = false;
			return false;
		}
	}
	
	/*
	 * Deposit passed amount into passed account
	 * 
	 * @param  a  Account to deposit to
	 * @param  ammount  Amount to deposit into Account
	 * 
	 * @return  true if successful Deposit
	 */
	public boolean Deposit(Account a, double ammount)
	{
		if(bank.Deposit(a, ammount))
		{
			successfulDeposit = true;
			return true;
		}
		else
		{
			successfulDeposit = false;
			return false;
		}
	}
	
	/*
	 * Gets a String representation of passed Account
	 * 
	 * @param  a  Account to get String of
	 * 
	 * @return  String representation of a
	 */
	public String getDisplay(Account a)
	{
		String ans = "===========================\n";
		ans +=       "Account Type: " + a.getType() + "\n";
		ans +=       "Account Number: " + a.getAccountNum() + "\n";
		ans +=       "Balance: $" + a.getBalance() + "\n";
		ans +=       "===========================";
		return ans;
	}
	
	/*
	 * returns a list of Accounts in the bank
	 * 
	 * @return  a list of Accounts in the bank
	 */
	public ArrayList<Account> getAccounts()
	{
		return accounts;
	}
	
	/*
	 * Sets currents step to determine action
	 * 
	 * @param  num  Number to set to stepNum
	 */
	public void setStep(int num)
	{
		stepNum = num;
	}
	
	/*
	 * Sets current transaction step to determine action
	 * 
	 * @param  num  Number to set to transStepNum
	 */
	public void setTransStep(int num)
	{
		transStepNum = num;
	}
	
	/*
	 * returns what the current prompt is according to
	 *  stepNum and transStepNum
	 *  
	 * @return  current prompt
	 */
	public String getPrompt()
	{
		return prompt;
	}
	
	/*
	 * Sets current Account the ATM is dealing with
	 * 
	 * @param  a  Account to set to currentAccount
	 */
	public void setCurrentAccount(Account a)
	{
		currentAccount = a;
	}
	
	/*
	 * Sets the prompt the ATM should be on
	 */
	public void setPrompt()
	{
		//stepNum = 0 get Account Number
		if(stepNum == 0)
			prompt = "Enter Account Number:";
		//stepNum = 1 get PIN Number
		if(stepNum == 1)
			prompt = "Enter PIN:";
		//step = 2 Main Transaction Menu
		if(stepNum == 2)
			prompt = "Transaction Menu:\n"
					+ "0.Balance Inquiry\n"
					+ "1.Deposit Money\n"
					+ "2.Withdraw Money\n"
					+ "3.Log Off\n"
					+ "Press Number of Option then select OK";
		if(stepNum == 3)
		{
			//if transStepNum = 0
			if(transStepNum == 0)
				//Display the current Account's Balance Inquiry
				prompt = getDisplay(currentAccount);
		}
		if(stepNum == 4)
		{
			//if transStepNum = 1 Get Deposit Amount
			if(transStepNum == 1)
				prompt = "Enter Deposit Ammount";
			//if transStepNum = 2 Get Withdraw Amount
			if(transStepNum == 2)
				prompt = "Enter Withdraw Ammount";
		}
		if(stepNum == 5)
		{
			if(transStepNum == 1)
			{
				//if the most recent deposit was successful,
				// Successful Deposit
				if(successfulDeposit)
					prompt = "Deposit Successful";
				else //Failed
				{
					prompt = "Deposit Failed";
					if(currentAccount.getType().equals("CD"))
						prompt += "\n\nBalance Under Minimum\n"
								+ "Unable to open CD Account";
				}
			}
			if(transStepNum == 2)
			{
				//if the most recent withdraw was successful,
				// Successful Withdraw
				if(successfulWithdraw)
					prompt = "Withdraw Successful";
				else //Failed
				{
					prompt = "Withdraw Failed";
					if(currentAccount.getType().equals("CD"))
						prompt += "\n\nCannot Withdraw from a CD Account";
				}
			}
		}
		//Has Changed, so notify
		setChanged();
		notifyObservers();
	}
}







