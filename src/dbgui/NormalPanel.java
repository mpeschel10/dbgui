package dbgui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class NormalPanel extends JPanel {
	private DBFrame parent;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBFrame frame = new DBFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.tryLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static final long serialVersionUID = 8452604351264466311L;
	JTabbedPane tabNormal;
	QueryTabPanel pnlTabQuery;

	/**
	 * Create the panel.
	 */
	public NormalPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		tabNormal = new JTabbedPane(JTabbedPane.TOP);
		add(tabNormal);
		
		pnlTabQuery = new QueryTabPanel();
		pnlTabQuery.setNormalPanel(this);
		tabNormal.addTab("Query", null, pnlTabQuery, null);
		pnlTabQuery.setLayout(new BoxLayout(pnlTabQuery, BoxLayout.Y_AXIS));
		
	}
	
	public List<String> runQuery(String s) { return this.parent.runQuery(s); }
	public void assignFocus() { this.tabNormal.getSelectedComponent().requestFocus(); }
	public void setParent(DBFrame parent) { this.parent = parent; }

}
