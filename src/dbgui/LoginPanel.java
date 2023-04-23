package dbgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = -6454644601797406258L;
	private JTextField txtPassword;
	private JTextField txtUsername;

	public LoginPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblRemcoInsuranceCorp = new JLabel("RemCo Insurance Corp.");
		lblRemcoInsuranceCorp.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(lblRemcoInsuranceCorp);
		
		JLabel lblInsuranceDatabase = new JLabel("Insurance Database Login");
		add(lblInsuranceDatabase);
		
		JSplitPane spltUsername = new JSplitPane();
		spltUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(spltUsername);
		
		JLabel lblUsername = new JLabel("Username:");
		spltUsername.setLeftComponent(lblUsername);
		
		txtUsername = new JTextField();
		spltUsername.setRightComponent(txtUsername);
		txtUsername.setColumns(10);
		
		JSplitPane spltPassword = new JSplitPane();
		spltPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(spltPassword);
		
		txtPassword = new JTextField();
		spltPassword.setRightComponent(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		spltPassword.setLeftComponent(lblPassword);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("God save us");
			}
		});
		add(btnLogIn);
	}

}
