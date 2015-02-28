/*
 * Bank.java
 *
 * Version:
 *  $Id: Bank.java,v 1.5 2013/12/07 04:47:51 jxz5746 Exp $
 * Revisions:
 *  $Log: Bank.java,v $
 *  Revision 1.5  2013/12/07 04:47:51  jxz5746
 *  Finished
 *
 *  Revision 1.4  2013/12/07 04:09:48  jxz5746
 *  Commented. Works.
 *
 *  Revision 1.3  2013/12/07 01:43:29  jxz5746
 *  Should be done. Needs Comments
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
 * Representation of a bank
 * 
 * @author Jacob Zuber
 */

import java.io.*;
import java.util.*;

public class Bank extends Observable
{
	private static boolean isBatch;
	private ArrayList<Account> accounts;
	
	/*
	 * Constructor
	 *  Creates a bank of passed accounts
	 *  
	 * @param  accounts  Accounts to be added to the bank
	 */
	public Bank(ArrayList<Account> accounts)
	{
		this.accounts = new ArrayList<Account>();
		for(int i = 0; i < accounts.size(); i++)
		{
			addAccount(accounts.get(i));
		}
	}
	
	/*
	 * Adds account to the bank
	 * 
	 * @param  act  Account to be added to the bank
	 */
	public void addAccount(Account act)
	{
		this.accounts.add(act);
		setChanged();
		notifyObservers();
	}
	
	/*
	 * returns the list of accounts in the bank
	 * 
	 * @return  list of accounts in the bank
	 */
	public ArrayList<Account> getAccounts()
	{
		return accounts;
	}
	
	/*
	 * Withdraws designated amount from designated account
	 * 
	 * @param  a  account to withdraw from
	 * @param  ammount  Amount to withdraw
	 * 
	 * @return  true if withdraw was successful,
	 *           else false
	 */
	public boolean Withdraw(Account a, double ammount)
	{
		double bal = a.getBalance();
		boolean ans = false;
		if(!a.canOpen() || bal - ammount < 0)
		{
			ans = false;
		}
		else
		{
			ans = a.Withdraw(ammount);
			setChanged();
			notifyObservers();
		}
		return ans;
	}
	
	/*
	 * Deposits designated amount to designated account
	 * 
	 * @param  a  account to deposit to
	 * @param  ammount  Amount to deposit
	 * 
	 * @return  true if deposit was successful,
	 *           else false
	 */
	public boolean Deposit(Account a, double ammount)
	{
		if(a.canOpen())
		{
			a.Deposit(ammount);
			setChanged();
			notifyObservers();
			return true;
		}
		else
			return false;
	}
	
	/*
	 * returns a String representation of the Bank
	 * 
	 * return  String representation of the Bank
	 */
	public String toString()
	{
		String Data = "\n"
				+	"Account Type    Account Balance\n"
				+	"------------    ------- -----------\n";
			
		for(int i = 0; i < accounts.size(); i++)
		{
			Account account = accounts.get(i);
			Data += account.getType() + "\t";
			if(account.getType().equals("CD") || 
					account.getType().equals("Saving"))
				Data += "\t";
			Data += ""
				+ account.getAccountNum() 
				+ "\t$" + account.getBalance() + "\n";
		}
		Data += "\n"
		+	"=============================================";
		return Data;
	}
	
	/*
	 * Main method to run the bank
	 * 
	 * @param  args  Command line arguments
	 * 
	 * @exception  IOException  if an IOException occurs
	 */
	public static void main(String args[]) throws IOException
	{
		//Error if there are not 1 or 2 arguments
		if(args.length != 1 && args.length != 2)
		{
			System.err.println(
					"Usage: java Bank bankFile [batchFile]");
			System.exit(0);
		}
		
		isBatch = false;
		
		//Get readers to read each file
		BufferedReader bankFile = null;
		BufferedReader batchFile = null;
		try {
			bankFile = new BufferedReader ( new FileReader (args[0]));
		} catch (FileNotFoundException e) {
			System.err.println(args[0] + " not found.");
			System.exit(0);
		}
		if(args.length == 2)
		{
			//there is a batch file
			isBatch = true;
			try {
				batchFile = new BufferedReader ( new FileReader (args[1]));
			} catch (FileNotFoundException e) {
				System.err.println(args[1] + " not found.");
				System.exit(0);
			}
		}
		
		//Run through bankFile getting correct 
		// info to make the accounts to add to the bank
		ArrayList<Account> accounts = new ArrayList<Account>();
		String next = bankFile.readLine();
		while(next != null)
		{
			//Getting type of account
			int spaceNum = next.indexOf(" ");
			String type = next.substring(0, spaceNum);
			//Getting Account(ID) Number
			next = next.substring(spaceNum+1);
			spaceNum = next.indexOf(" ");
			int AccountNum = Integer.parseInt(next.substring(0, spaceNum));
			//Getting Account PIN
			next = next.substring(spaceNum+1);
			spaceNum = next.indexOf(" ");
			int PIN = Integer.parseInt(next.substring(0, spaceNum));
			next = next.substring(spaceNum+1);
			//Getting Balance
			double Balance = Double.parseDouble(next);
			
			//if CD, Make CD
			if(type.equalsIgnoreCase("CD"))
			{
				accounts.add(new CD(AccountNum, PIN, Balance));
			}
			//if Checking, Make Checking
			else if(type.equalsIgnoreCase("Checking"))
			{
				accounts.add(new Checking(AccountNum, PIN, Balance));
			}
			//if Saving, Make Saving
			else if(type.equalsIgnoreCase("Saving"))
			{
				accounts.add(new Saving(AccountNum, PIN, Balance));
			}
			//else, Error
			else
			{
				System.err.println("Invalid Account Name");
				System.exit(0);
			}
			
			//set next to the next line of the file
			next = bankFile.readLine();
		}
		bankFile.close();
		
		//Create Bank
		Bank b = new Bank(accounts);
		if(isBatch)
		{
			//==============================
			//      Initial Bank Data
			//==============================
			String initialData = 
					"========== Initial Bank Data ==================\n"
					+ b.toString();
			System.out.println(initialData);
			
			//Response to each step
			String response = "";
			next = batchFile.readLine();
			while(next != null)
			{
				char command = next.charAt(0);
				//==============================
				//        Open Account
				//==============================
				if(command == 'o')
				{
					int AccountNum = -1;
					int PIN = -1;
					double balance = -1;
					
					next = next.substring(2);
					char type = next.charAt(0);
					
					//Getting AccountNum
					next = next.substring(2);
					int spaceNum = next.indexOf(" ");
					AccountNum = Integer.parseInt(
							next.substring(0, spaceNum));
					
					//Getting PIN
					next = next.substring(spaceNum+1);
					spaceNum = next.indexOf(" ");
					PIN = Integer.parseInt(next.substring(0, spaceNum));
					
					//Getting Balance
					next = next.substring(spaceNum+1);
					balance = Double.parseDouble(next);
					
					//Creating Account
					Account a = null;
					if(type == 'x')
					{
						a = new Checking(AccountNum, PIN, balance);
					}
					else if(type == 'c')
					{
						a = new CD(AccountNum, PIN, balance);
					}
					else if(type == 's')
					{
						a = new Saving(AccountNum, PIN, balance);
					}
					else
					{
						System.err.println("Invalid Account Type");
						System.exit(0);
					}
					
					//if account can open
					if(a.canOpen())
					{
						//add it
						accounts.add(a);
						response = AccountNum + "\t" + command + "\t" + type
								+ "\tOpen: Succesful" + "\t\t$" + balance;
					}
					else //don't add it
					{
						
						response = AccountNum + "\t" + command
								+ "\t" + type + "\tOpen: Failed";
					}
				}
				//==============================
				//        Close Account
				//==============================				
				else if(command == 'c')
				{
					//Getting Account Number
					next = next.substring(2);
					int AccountNum = Integer.parseInt(next);
					Account account = null;
					for(int i = 0; i < accounts.size(); i++)
					{
						account = accounts.get(i);
						//if Account found in list, remove it
						if(account.getAccountNum() == AccountNum)
						{
							accounts.remove(i);
							response = AccountNum + "\t" + command
									+ "\tClosed: Success \t$" 
									+ account.getBalance();
							break;
						}
						else //Failed to remove
						{
							response = AccountNum + "\t" +
									command + "\tClosed: Failed";
						}
						
					}
					//No Accounts exist, Failed to remove
					if(account == null)
					{
						response = AccountNum + "\t" +
								command + "\tClosed: Failed";
					}
				}
				//==============================
				//          Deposit
				//==============================
				else if(command == 'd')
				{
					//Getting Account Number
					next = next.substring(2);
					int spaceNum = next.indexOf(" ");
					int AccountNum = Integer.parseInt(
							next.substring(0, spaceNum));
					//Getting Deposit Amount
					next = next.substring(spaceNum+1);
					double depositAmmount = Double.parseDouble(next);
					
					for(int i = 0; i < accounts.size(); i++)
					{
						Account account = accounts.get(i);
						//if Account found
						if(account.getAccountNum() == AccountNum)
						{
							//Deposit amount
							account.Deposit(depositAmmount);
							response = AccountNum + "\t" + command 
									+ "\t\t$" + depositAmmount 
									+ "\t\t\t$" + account.getBalance();
							//get out of loop
							break;
						}
						else //Failure
						{
							response = AccountNum + "\t" + command
									+ "\t\t$" + depositAmmount 
									+ "\t\t\tFailed";
						}
					}
				}
				//==============================
				//           Withdraw
				//==============================
				else if(command == 'w')
				{
					//Getting Account Number
					next = next.substring(2);
					int spaceNum = next.indexOf(" ");
					int AccountNum = Integer.parseInt(
							next.substring(0, spaceNum));
					//Getting withdraw amount
					next = next.substring(spaceNum+1);
					double withdrawAmmount = Double.parseDouble(next);
					
					for(int i = 0; i < accounts.size(); i++)
					{
						Account account = accounts.get(i);
						//if account found
						if(account.getAccountNum() == AccountNum)
						{
							//if can withdraw
							if(account.Withdraw(withdrawAmmount))
							{
								//withdraw amount
								response = AccountNum + "\t" + command 
									+ "\t\t$" + withdrawAmmount 
									+ "\t\t\t$" + account.getBalance();
							}
							//get out of loop
							break;
						}
						else //Failure
						{
							response = AccountNum + "\t" + command
									+ "\t\t$" + withdrawAmmount 
									+ "\t\t\tFailed";
						}
					}
				}
				//==============================
				//      Interest/Penalty
				//==============================
				else if(command == 'a')
				{
					response = "\n"
						+	"============== Interest Report ==============\n"
						+	"Account Adjustment      New Balance\n"
						+	"------- -----------     -----------";
					for(int i = 0; i < accounts.size(); i++)
					{
						//Add interest and penalty to each account
						Account account = accounts.get(i);
						response += "\n" + account.getAccountNum() + "\t$"
								+ (account.addInterest() - account.addPenalty())
								+ "\t\t$" + account.getBalance();
					}
					response += "\n============================="
								+ "================\n";
				}
				else //Invalid command
				{
					System.err.println("Invalid Command");
					System.exit(0);
				}
				
				System.out.println(response);
				next = batchFile.readLine();
			}
			
			//===============================
			//     Print Final Bank Data
			//===============================
			String finalData = "\n"
				+	"========== Final Bank Data ==================\n"
					+b.toString();
			System.out.println(finalData);
			batchFile.close();
		}
		//======================
		//     If Not Batch
		//======================
		else
		{
			//Create a BankGUI
			new BankGUI(b);
		}
	}
}










