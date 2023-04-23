package dbgui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DBFrame extends JFrame {
	private static final long serialVersionUID = -4193289423026705982L;
	private JPanel contentPane;
	private LoginPanel loginPanel;

	public DBFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		loginPanel = new LoginPanel();
		contentPane = loginPanel;
		setContentPane(contentPane);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

}
