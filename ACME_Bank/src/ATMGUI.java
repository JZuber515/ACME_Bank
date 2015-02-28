/*
 * ATMGUI.java
 *
 * Version:
 *  $Id: ATMGUI.java,v 1.4 2013/12/07 04:47:51 jxz5746 Exp $
 * Revisions:
 *  $Log: ATMGUI.java,v $
 *  Revision 1.4  2013/12/07 04:47:51  jxz5746
 *  Finished
 *
 *  Revision 1.3  2013/12/07 04:09:48  jxz5746
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
 * GUI Representation of an ATM
 * 
 * @author Jacob Zuber
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


@SuppressWarnings("serial")
public class ATMGUI extends JFrame implements Observer
{
	private Container container;
	private JTextField textField;
	private JTextArea prompt;
	private JPanel pane;
	
	private int stepNum;
	private int transStepNum;
	
	private ATM atm;
	private String txtNum;
	private String display;
	private ArrayList<Account> accounts;
	private Account currentAccount;
	
	/*
	 * Constructor
	 *  Creates GUI representation of passed ATM
	 */
	public ATMGUI(final ATM atm)
	{
		atm.addObserver(this);
		this.accounts = atm.getAccounts();
		this.atm = atm;
		stepNum = 0;
		transStepNum = -1;
		txtNum = "";
		display = "";
		//Basic GUI Setup
		textField = new JTextField("");
		container = getContentPane();
		container.setLayout(new BorderLayout());
		setVisible(true);
		setResizable(false);
		setLocation(620, 100);
		setSize(400, 600);
		int ATM_NUM = (int)(Math.random()*10000)+1;
		setTitle("Jacob Zuber ATM " + ATM_NUM);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel bigPane = new JPanel();
		bigPane.setLayout(new GridLayout(2, 1));
		
		//========================
		//Text Fields
		//========================
		pane = new JPanel(new GridLayout(2, 1));
		prompt = new JTextArea("");
		prompt.setForeground(Color.WHITE);
		prompt.setBackground(Color.BLACK);
		atm.setPrompt();
		pane.add(prompt);
								
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		pane.add(textField);
		
		bigPane.add(pane);
		
		//====================
		//Numbers 1-9
		//====================
		pane = new JPanel();
		pane.setLayout(new GridLayout(4, 3));
		for(int i = 1; i <= 9; i++)
		{
			JButton button = new JButton("" + i);
			button.setForeground(Color.BLACK);
			button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					Integer num = Integer.parseInt(e.getActionCommand());
					txtNum += num;
					//if step is not entering pin number step
					if(stepNum != 1)
					{
						display = textField.getText() + num;
					}
					else
					{
						display += "*";
					}
					textField.setText(display);
				}
			});
			pane.add(button);
		}
		
		//======================
		//Cancel and Close
		//======================
		JPanel jpane = new JPanel();
		jpane.setLayout(new GridLayout(2, 1));
		JButton button = new JButton("Cancel");
		button.setForeground(Color.RED);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//if before Main Menu,
				// send to getting Account Number
				if(stepNum < 2)
				{
					stepNum = 0;
					atm.setStep(0);
				}
				//if after Main Menu,
				// send back to Main Menu
				if(stepNum > 2)
				{
					stepNum = 2;
					atm.setStep(2);
				}
				if(transStepNum > -1)
				{
					transStepNum = -1;
					atm.setTransStep(-1);
				}
				atm.setPrompt();
				clear();
			}
		});
		jpane.add(button);
		button = new JButton("Close");
		button.setForeground(Color.BLUE);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//get rid of window
				dispose();
			}
		});
		jpane.add(button);
		pane.add(jpane);
		
		//====================
		//Number 0 and Decimal Point
		//====================
		jpane = new JPanel();
		jpane.setLayout(new GridLayout(2, 1));
		button = new JButton("0");
		button.setForeground(Color.BLACK);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Integer num = Integer.parseInt(e.getActionCommand());
				txtNum += num;
				//if step is not entering pin number step
				if(stepNum != 1)
				{	
					display = textField.getText() + num;
				}
				else
				{
					display += "*";
				}
				textField.setText(display);
			}
		});
		jpane.add(button);
		button = new JButton(".");
		button.setForeground(Color.BLACK);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//Only usable when enter amount
				// to withdraw or deposit
				if(stepNum == 4)
				{
					display = textField.getText() + ".";
					textField.setText(display);
				}
			}
			
		});
		jpane.add(button);
		pane.add(jpane);
		//=====================
		//OK and Clear
		//=====================
		jpane = new JPanel();
		jpane.setLayout(new GridLayout(2, 1));
		button = new JButton("OK");
		button.setForeground(Color.GREEN);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				if(stepNum == 0)
				{
					for(int i = 0; i < accounts.size(); i++)
					{
						//if entered Account Number matches an Account
						if(atm.isValidAccountNumber(accounts.get(i),
								Integer.parseInt(txtNum)))
						{
							//Set that account to the currentAccount
							currentAccount = accounts.get(i);
							atm.setCurrentAccount(currentAccount);
							//increment step
							stepNum++;
							atm.setStep(stepNum);
							clear();
							//get out of loop
							break;
						}
					}
					//if Account not found
					if(currentAccount == null)
					{
						clear();
						prompt.setText(prompt.getText() +
								"\n\nInvalid Account Number");
					}
					return;
				}
				if(stepNum == 1)
				{
					//if entered PIN Number matches currentAccount
					if(atm.isValidPIN(currentAccount,
							Integer.parseInt(txtNum)))
					{
						//increment step
						stepNum++;
						atm.setStep(stepNum);
						clear();
					}
					else //Invalid PIN
					{
						clear();
						prompt.setText(prompt.getText() +
								"\n\nInvalid PIN Number");
					}
					return;
				}
				if(stepNum == 2)
				{
					atm.setPrompt();
					//if entered number is a valid number, transStepNum is set
					if(setTransStepNum(Integer.parseInt(textField.getText())))
					{
						//increment step
						atm.setTransStep(transStepNum);
						stepNum++;
						atm.setStep(stepNum);
						clear();
					}
					else //Failed
					{
						clear();
						prompt.setText(prompt.getText() 
								+ "\n\nInvalid Number");
						return;
					}
				}
				if(stepNum == 3)
				{
					//Balance Inquiry chosen
					if(transStepNum == 0)
					{
						//Show balance inquiry
						clear();
						stepNum++;
						atm.setStep(stepNum);
						prompt.setText(prompt.getText() 
								+ "\n\nOK to continue");
						return;
					}
					//Withdraw or Deposit chosen
					if(transStepNum == 1 || transStepNum == 2)
					{
						//increment step
						stepNum++;
						atm.setStep(stepNum);
						clear();
						return;
					}
					//Log off chosen
					if(transStepNum == 3)
					{
						//Go back to step 0
						stepNum = 0;
						atm.setStep(stepNum);
						transStepNum = -1;
						atm.setTransStep(transStepNum);
						clear();
						return;
					}
				}
				if(stepNum == 4)
				{
					//Deposit
					if(transStepNum == 1)
					{
						//Deposit entered amount
						atm.Deposit(currentAccount,
								Double.parseDouble(textField.getText()));
						//increment step
						stepNum++;
						atm.setStep(stepNum);
						clear();
					}
					//Withdraw
					else if(transStepNum == 2)
					{
						//Withdraw entered amount
						atm.Withdraw(currentAccount, 
								Double.parseDouble(textField.getText()));
						//increment step
						stepNum++;
						atm.setStep(stepNum);
						clear();
					}
					else //go back to Main Menu
					{
						stepNum = 2;
						atm.setStep(stepNum);
						transStepNum = -1;
						atm.setTransStep(transStepNum);
						clear();
						return;
					}
				}
				if(stepNum == 5)
				{
					//Show if successful or not
					// go back to step 4 which will lead
					// to main menu when ok is pressed
					stepNum = 4;
					atm.setStep(stepNum);
					transStepNum = -1;
					atm.setTransStep(transStepNum);
					prompt.setText(prompt.getText() + "\n\nOK to continue");
					return;
				}
			}
		});
		jpane.add(button);
		button = new JButton("Clear");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//Clears entered numbers and resets prompt
				clear();
			}
		});
		button.setForeground(Color.YELLOW);
		jpane.add(button);
		pane.add(jpane);
		
		bigPane.add(pane);
		
		container.add(bigPane);
	}
	/*
	 * Clears entered numbers and resets prompt
	 */
	private void clear()
	{
		txtNum = "";
		display = "";
		textField.setText("");
		atm.setPrompt();
	}
	
	/*
	 * Sets transStepNum and determines if
	 *  passed num is a valid transStepNum
	 *  
	 * @param  num  number to set to transStepNum
	 * 
	 * @return  true if the passed number is a valid transStepNum
	 */
	public boolean setTransStepNum(int num)
	{
		if(num == 0)
		{
			transStepNum = 0;
			return true;
		}
		else if(num == 1)
		{
			transStepNum = 1;
			return true;
		}
		else if(num == 2)
		{
			transStepNum = 2;
			return true;
		}
		else if(num == 3)
		{
			transStepNum = 3;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		//updates the prompt
		prompt.setText(atm.getPrompt());
	}
}












