package gr.aueb.cf.schoolapp.viewcontroller;

import gr.aueb.cf.schoolapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TeachersMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public TeachersMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		setTitle("Μενού Εκπαιδευτών");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton teachersViewButton = new JButton("Προβολή Εκπαιδευτών");
		teachersViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersUpdateDeleteFrame().setVisible(true);
				Main.getTeachersMenuFrame().setEnabled(true);
			}
		});
		teachersViewButton.setForeground(new Color(52, 101, 164));
		teachersViewButton.setBounds(123, 25, 193, 33);
		contentPane.add(teachersViewButton);
		
		JButton insertBtn = new JButton("Εισαγωγή Εκπαιδευτή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show Teachers Insert Window
				Main.getTeachersInsertFrame().setVisible(true);
				// Disable Teachers Menu Window
				Main.getTeachersMenuFrame().setEnabled(false);
			}
		});
		insertBtn.setForeground(new Color(52, 101, 164));
		insertBtn.setBounds(123, 83, 193, 33);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Re-enable Main menu
				Main.getMainMenuFrame().setEnabled(true);
				
				// Hide Teachers menu
				Main.getTeachersMenuFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(52, 101, 164));
		closeBtn.setBounds(288, 226, 117, 25);
		contentPane.add(closeBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 209, 416, 1);
		contentPane.add(separator);
	}

}
