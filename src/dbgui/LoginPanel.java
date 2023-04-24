package dbgui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = -6454644601797406258L;
	private JTextField txtPassword;
	private JTextField txtUsername;
	protected JButton btnLogIn;
	private DBFrame parent;

	public LoginPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblRemcoInsuranceCorp = new JLabel("RemCo Insurance Corp.");
		add(lblRemcoInsuranceCorp);
		
		JLabel lblInsuranceDatabase = new JLabel("Insurance Database Login");
		add(lblInsuranceDatabase);
		
		JPanel pnlUsername = new JPanel();
		pnlUsername.setLayout(new BoxLayout(pnlUsername, BoxLayout.X_AXIS));
		add(pnlUsername);
		
		JLabel lblUsername = new JLabel("Username: ");
		pnlUsername.add(lblUsername);
		
		txtUsername = new JTextField();
		pnlUsername.add(txtUsername);
		txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtUsername.getPreferredSize().height));
		txtUsername.setText("tcl5238");
		txtUsername.setColumns(10);
		
		JPanel pnlPassword = new JPanel();
		pnlPassword.setLayout(new BoxLayout(pnlPassword, BoxLayout.X_AXIS));
		add(pnlPassword);
		
		JLabel lblPassword = new JLabel("Password: ");
		pnlPassword.add(lblPassword);
		
		txtPassword = new JTextField();
		pnlPassword.add(txtPassword);
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtPassword.getPreferredSize().height));
		txtPassword.setText("tcl5238");
		txtPassword.setColumns(10);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.tryLogin();
			}
		});
		add(btnLogIn);
	}
	
	public void setParent(DBFrame parent) { this.parent = parent; }
	public void focusBtnLogIn() { btnLogIn.requestFocus(); }
	
	protected String getUsername() { return txtUsername.getText(); }
	protected String getPassword() { return txtPassword.getText(); }
}
