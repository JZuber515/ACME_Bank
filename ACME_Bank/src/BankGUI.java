/*
 * BankGUI.java
 *
 * Version:
 *  $Id: BankGUI.java,v 1.4 2013/12/07 04:09:48 jxz5746 Exp $
 * Revisions:
 *  $Log: BankGUI.java,v $
 *  Revision 1.4  2013/12/07 04:09:48  jxz5746
 *  Commented. Works.
 *
 *  Revision 1.3  2013/12/07 01:43:29  jxz5746
 *  Should be done. Needs Comments
 *
 *  Revision 1.2  2013/12/06 20:18:08  jxz5746
 *  Need to do batchFile and Comments
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
 * Display of a bank in GUI Format
 *  Can open ATMGUIs
 * 
 * @author Jacob Zuber
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class BankGUI extends JFrame implements Observer
{
	private Container container;
	private Bank bank;
	private JPanel pane;
	private JPanel pane1;
	
	/*
	 * Constructor
	 *  Creates a display of the passed bank
	 */
	public BankGUI(final Bank bank)
	{
		//Set Basic GUI up
		this.bank = bank;
		this.bank.addObserver(this);
		container = getContentPane();
		container.setLayout(new BorderLayout());
		setVisible(true);
		setResizable(false);
		setLocation(100, 100);
		setTitle("Jacob Zuber Bank");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		bank.addObserver(this);
		
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(1, 3));
		JTextField txt = new JTextField("Account Number");
		txt.setEditable(false);
		pane.add(txt);
		txt = new JTextField("Type");
		txt.setEditable(false);
		pane.add(txt);
		txt = new JTextField("Balance");
		txt.setEditable(false);
		pane.add(txt);
		container.add(pane);
		setSize(500, 100);
		showDisplay();
		update(null, null);
		
		this.addWindowListener(new WindowListener()
		{
			@Override
			//Do Nothing
			public void windowActivated(WindowEvent arg0) {
				
			}

			@Override
			//if BankGUI is Closed, print Final Bank Data
			public void windowClosed(WindowEvent arg0) {
				String finalData = "\n"
						+	"========== Final Bank Data ==================\n"
						+ bank.toString();
					System.out.println(finalData);
				
			}

			@Override
			//Do Nothing
			public void windowClosing(WindowEvent arg0) {
				
			}

			@Override
			//Do Nothing
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			//Do Nothing
			public void windowDeiconified(WindowEvent arg0) {
				
			}

			@Override
			//Do Nothing
			public void windowIconified(WindowEvent arg0) {
				
			}

			@Override
			//Do Nothing
			public void windowOpened(WindowEvent arg0) {
				
			}
		});
	}
	
	/*
	 * Set GUI Display
	 */
	public void showDisplay()
	{
		ArrayList<Account> accounts = bank.getAccounts();
		pane = new JPanel();
		pane.setLayout(new GridLayout(accounts.size()+1, 3));
		//Account Number Header
		JTextField txt = new JTextField("Account Number:");
		txt.setEditable(false);
		pane.add(txt);
		//Account Type Header
		txt = new JTextField("Type:");
		txt.setEditable(false);
		pane.add(txt);
		//Account Balance Header
		txt = new JTextField("Balance:");
		txt.setEditable(false);
		pane.add(txt);
		for(int i = 0; i < accounts.size(); i++)
		{
			//Account Number/ID
			txt = new JTextField(""+ accounts.get(i).getAccountNum());
			txt.setEditable(false);
			txt.setVisible(true);
			pane.add(txt);
			//Account Type
			txt = new JTextField(accounts.get(i).getType());
			txt.setEditable(false);
			pane.add(txt);
			//Account Balance
			txt = new JTextField("$"+accounts.get(i).getBalance());
			txt.setEditable(false);
			pane.add(txt);
			//Set Size depending on how many accounts there are
			setSize(500, 200+(i*100));
		}
		container.add(pane, BorderLayout.CENTER);
		
		pane1 = new JPanel();
		pane1.setLayout(new GridLayout(1, 2));
		
		//Update Button Updates the GUI
		JButton button = new JButton("Update");
		button.setSize(250, 50);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update(null, null);
			}
		});
		pane1.add(button);
		
		//Open ATM Button Opens ATM GUI
		button = new JButton("Open ATM");
		button.setSize(250, 50);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				new ATMGUI(new ATM(bank));
			}
		});
		pane1.add(button);
		container.add(pane1, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		container.remove(0);
		showDisplay();
	}
}
