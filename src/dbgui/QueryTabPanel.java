package dbgui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class QueryTabPanel extends JPanel {
	private static final long serialVersionUID = 1766245205619822423L;
	private JTextField txtQuery;
	private JButton btnQuery;
	private JTextArea txtResult;
	private NormalPanel normalPanel;

	public QueryTabPanel() {
		JPanel pnlQuery = new JPanel();
		pnlQuery.setLayout(new BoxLayout(pnlQuery, BoxLayout.X_AXIS));
		add(pnlQuery);
		
		JLabel lblQuery = new JLabel("Query: ");
		pnlQuery.add(lblQuery);
		
		txtQuery = new JTextField();
		txtQuery.setText("browseCustomer");
		pnlQuery.add(txtQuery);
		txtQuery.setColumns(10);
		
		QueryTabPanel me = this;
		btnQuery = new JButton("Run Query");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { me.runQuery(); }
		});
		pnlQuery.add(btnQuery);
		
		JPanel pnlResult = new JPanel();
		add(pnlResult);
		pnlResult.setLayout(new BoxLayout(pnlResult, BoxLayout.X_AXIS));
		
		txtResult = new JTextArea(" ");
		txtResult.setEditable(false);
		txtResult.setCursor(null);
		txtResult.setOpaque(false);
		txtResult.setFocusable(false);
		txtResult.setWrapStyleWord(true);
		txtResult.setLineWrap(true);
		txtResult.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		JScrollPane sclResult = new JScrollPane(txtResult);
		pnlResult.add(sclResult);
		pnlResult.setPreferredSize(sclResult.getPreferredSize());
		pnlResult.setMaximumSize(sclResult.getMaximumSize());
		
		pnlQuery.setMaximumSize(new Dimension(Integer.MAX_VALUE, pnlQuery.getPreferredSize().height));
	}
	
	public void runQuery() {
		List<String> result = this.normalPanel.runQuery(this.txtQuery.getText());
		this.txtResult.setText(String.join("\n",  result));
	}
	
	@Override
	public void requestFocus() { this.btnQuery.requestFocusInWindow(); }

	public void setNormalPanel(NormalPanel normalPanel) { this.normalPanel = normalPanel; }
}
