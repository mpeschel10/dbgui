package dbgui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class DBFrame extends JFrame {
	private static final long serialVersionUID = -4193289423026705982L;
	
	private JPanel contentPane;
	private LoginPanel loginPanel;
	private NormalPanel normalPanel;
	
	private JLabel lblStatus; 
	
	private DBCommands commands;

	public DBFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (commands != null) {
						commands.close();
						System.out.println("Closed database connection.");
					}
					System.exit(0);
				} catch (SQLException exception) {
					exception.printStackTrace();
					System.err.println("Could not close database connection?");
					System.exit(2);
				} catch (Exception exception) {
					exception.printStackTrace();
					System.err.println("An unanticipated error occurred closing the application.");
					System.exit(4);
				}
				System.exit(3);
			}
		});
		
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		loginPanel = new LoginPanel();
		loginPanel.setParent(this);
		contentPane.add(loginPanel, BorderLayout.CENTER);
		
		normalPanel = new NormalPanel();
		normalPanel.setParent(this);
		
		lblStatus = new JLabel("Status: Waiting.");
		contentPane.add(lblStatus, BorderLayout.SOUTH);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e);
			}
		});
	}
	
	public List<String> runQuery(String joined_args) {
		try {
			setStatus("Running query " + joined_args);
			List<String> result = commands.interpret(joined_args.split(" "));
//			setStatus("Query complete.");
			return result;
		} catch (SQLException exception) {
			exception.printStackTrace();
			setStatus("An exception has occurred: " + exception.toString());
			return Arrays.asList("An exception has occurred.");
		}
	}
	
	public void setStatus(String status) {
		lblStatus.setText("Status: " + status);
	}
	
	public void tryLogin() {
		try {
			setStatus("Opening connection...");
			commands = new DBCommands(loginPanel.getUsername(), loginPanel.getPassword());
			this.contentPane.remove(loginPanel);
			this.contentPane.add(normalPanel, BorderLayout.CENTER);
			this.normalPanel.assignFocus();
			setStatus("Connection successful.");
		} catch (SQLException e) {
			System.err.println(e);
			setStatus("Connection failed; SQLException; username or password may be incorrect.");
		} catch (ClassNotFoundException e) {
			System.err.println(e);
			setStatus("Connection failed; ClassNotFoundException; are you running this program with -classpath ojdbc11.jar:. ?");
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		loginPanel.focusBtnLogIn();
		SwingUtilities.getRootPane(loginPanel.btnLogIn).setDefaultButton(loginPanel.btnLogIn);
	}
}
