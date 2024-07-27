package gr.aueb.cf.schoolapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StudentsInsertFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField studentLnameText;
	private JTextField studentFnameText;
	private JLabel errorStudentFname;
	private JLabel errorStudentLname;
	
	/**
	 * Create the frame.
	 */
	public StudentsInsertFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				cleanText();
			}
			@Override
			public void windowActivated(WindowEvent e) {
				cleanText();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		setTitle("Εισαγωγή Εκπαιδευόμενου");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(76, 12, 281, 148);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel studentFnameLabel = new JLabel("Όνομα");
		studentFnameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		studentFnameLabel.setForeground(new Color(32, 74, 135));
		studentFnameLabel.setBounds(12, 34, 52, 15);
		panel.add(studentFnameLabel);
		
		JLabel studentLnameLabel = new JLabel("Επώνυμο");
		studentLnameLabel.setForeground(new Color(32, 74, 135));
		studentLnameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		studentLnameLabel.setBounds(12, 93, 64, 15);
		panel.add(studentLnameLabel);
		
		studentFnameText = new JTextField();
		studentFnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname = studentFnameText.getText().trim();
				validateFirstname(inputFirstname);
			}
		});
		studentFnameText.setColumns(10);
		studentFnameText.setBounds(82, 28, 170, 27);
		panel.add(studentFnameText);
		
		studentLnameText = new JTextField();
		studentLnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname = studentLnameText.getText().trim();
				validateLastname(inputLastname);
			}
		});
		studentLnameText.setColumns(10);
		studentLnameText.setBounds(82, 87, 170, 27);
		panel.add(studentLnameText);
		
		errorStudentFname = new JLabel("");
		errorStudentFname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorStudentFname.setForeground(new Color(204, 0, 0));
		errorStudentFname.setBounds(82, 55, 170, 20);
		panel.add(errorStudentFname);
		
		errorStudentLname = new JLabel("");
		errorStudentLname.setForeground(new Color(204, 0, 0));
		errorStudentLname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorStudentLname.setBounds(82, 113, 170, 20);
		panel.add(errorStudentLname);
		
		JButton btnNewButton = new JButton("Εισαγωγή");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Data binding
				String inputFirstname = studentFnameText.getText().trim();
				String inputLastname = studentLnameText.getText().trim();
				
				// ------------------------------ Validation --------------------------------
				validateFirstname(inputFirstname);
				validateLastname(inputLastname);
				if (inputFirstname.isEmpty() || inputLastname.isEmpty()) {
					return;
				}
				// --------------------------------------------------------------------------
				
				String sql = "INSERT INTO students (firstname, lastname) VALUES (?, ?)";
				
				try (Connection connection = DBUtil.getConnection();
					 PreparedStatement ps = connection.prepareStatement(sql)) {
					ps.setString(1, inputFirstname);
					ps.setString(2, inputLastname);
					int n = ps.executeUpdate();
					JOptionPane.showMessageDialog(null, n + " record(s) inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
				} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "Insertion Error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setForeground(new Color(32, 74, 135));
		btnNewButton.setBounds(158, 172, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenuFrame().setEnabled(true);
				Main.getStudentsInsertFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(32, 74, 135));
		closeBtn.setBounds(300, 233, 117, 25);
		contentPane.add(closeBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 222, 416, 1);
		contentPane.add(separator);
	}
	
	private void validateFirstname(String inputFirstname) {		
		if(inputFirstname.isEmpty()) {
			errorStudentFname.setText("Το όνομα είναι υποχρεωτικό.");
		}
		
		if (!inputFirstname.isEmpty()) {
			errorStudentFname.setText("");
		}
	}
	
	private void validateLastname(String inputLastname) {		
		if(inputLastname.isEmpty()) {
			errorStudentLname.setText("Το επώνυμο είναι υποχρεωτικό");
		}
		
		if (!inputLastname.isEmpty()) {
			errorStudentLname.setText("");
		}
	}
	
	private void cleanText() {
		studentFnameText.setText("");
		studentLnameText.setText("");
		errorStudentFname.setText("");
		errorStudentLname.setText("");
	}
}
