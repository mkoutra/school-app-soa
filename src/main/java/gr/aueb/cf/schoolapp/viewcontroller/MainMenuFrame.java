package gr.aueb.cf.schoolapp.viewcontroller;

import gr.aueb.cf.schoolapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel header = new JPanel();
	
	/**
	 * Create the frame.
	 */
	public MainMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		setTitle("Ποιότητα στην εκπαίδευση");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator headerSeparator = new JSeparator();
		headerSeparator.setBounds(0, 69, 468, 0);
		contentPane.add(headerSeparator);
		header.setBackground(new Color(32, 74, 135));
		header.setBounds(0, 0, 468, 57);
		contentPane.add(header);
		header.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Coding Factory");
		lblNewLabel.setBounds(12, 12, 119, 17);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel.setForeground(new Color(238, 238, 236));
		header.add(lblNewLabel);
		
		JButton teachersBtn = new JButton("");
		teachersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Teachers Menu becomes visible
				Main.getTeachersMenuFrame().setVisible(true);
				
				// Main frame becomes visible but cannot be pressed.
				Main.getMainMenuFrame().setEnabled(false);
			}
		});
		teachersBtn.setBounds(12, 94, 40, 40);
		contentPane.add(teachersBtn);
		
		JLabel teachersLabel = new JLabel("Εκπαιδευτές");
		teachersLabel.setBounds(70, 94, 110, 40);
		contentPane.add(teachersLabel);
		
		JButton studentsBtn = new JButton("");
		studentsBtn.setBounds(12, 157, 40, 40);
		contentPane.add(studentsBtn);
		
		JLabel studentsLabel = new JLabel("Εκπαιδευόμενοι");
		studentsLabel.setBounds(70, 157, 110, 40);
		contentPane.add(studentsLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(211, 215, 207));
		panel.setBounds(0, 257, 468, 53);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Εγχειρίδιο Χρήσης");
		label.setForeground(new Color(52, 101, 164));
		label.setBounds(12, 26, 126, 15);
		panel.add(label);
	}
	
}
