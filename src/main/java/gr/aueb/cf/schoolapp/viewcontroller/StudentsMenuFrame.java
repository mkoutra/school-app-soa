package gr.aueb.cf.schoolapp.viewcontroller;

import gr.aueb.cf.schoolapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentsMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public StudentsMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		setTitle("Μενού Εκπαιδευόμενων");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton studentsViewButton = new JButton("Προβολή Εκπαιδευόμενων");
		studentsViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsUpdateDeleteFrame().setVisible(true);
				Main.getStudentsMenuFrame().setEnabled(false);
			}
		});
		studentsViewButton.setForeground(new Color(32, 74, 135));
		studentsViewButton.setBounds(100, 39, 236, 39);
		contentPane.add(studentsViewButton);
		
		JButton studentInsertBtn = new JButton("Εισαγωγή Εκπαιδευόμενου");
		studentInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsInsertFrame().setVisible(true);
				Main.getStudentsMenuFrame().setEnabled(false);
			}
		});
		studentInsertBtn.setForeground(new Color(32, 74, 135));
		studentInsertBtn.setBounds(100, 112, 236, 39);
		contentPane.add(studentInsertBtn);
		
		JButton btnNewButton = new JButton("Κλείσιμο");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getMainMenuFrame().setEnabled(true);
				Main.getStudentsMenuFrame().setVisible(false);
			}
		});
		btnNewButton.setForeground(new Color(32, 74, 135));
		btnNewButton.setBounds(311, 226, 117, 25);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 216, 416, 1);
		contentPane.add(separator);
	}
}
