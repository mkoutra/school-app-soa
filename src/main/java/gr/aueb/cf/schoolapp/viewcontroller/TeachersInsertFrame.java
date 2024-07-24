package gr.aueb.cf.schoolapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class TeachersInsertFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JLabel errorFirstname;
	private JLabel errorLastname;

	/**
	 * Create the frame.
	 */
	public TeachersInsertFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				firstnameText.setText("");
				lastnameText.setText("");
				
			}
		});
		setTitle("Εισαγωγή Εκπαιδευτή");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(37, 34, 339, 139);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel firstnameLabel = new JLabel("Όνομα");
		firstnameLabel.setBounds(27, 18, 53, 15);
		panel.add(firstnameLabel);
		firstnameLabel.setForeground(new Color(52, 101, 164));
		
		firstnameText = new JTextField();
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname;
				
				inputFirstname = firstnameText.getText().trim(); 
				if(inputFirstname.equals("")) {
					errorFirstname.setText("Το όνομα είναι υποχρεωτικό.");
				}
				
				if (!inputFirstname.equals("")) {
					errorFirstname.setText("");
				}
			}
		});
		firstnameText.setBounds(85, 12, 222, 26);
		panel.add(firstnameText);
		firstnameText.setColumns(10);
		
		errorFirstname = new JLabel("");
		errorFirstname.setBounds(85, 39, 222, 26);
		panel.add(errorFirstname);
		
		lastnameText = new JTextField();
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname;
				
				inputLastname = lastnameText.getText().trim();
				
				if(inputLastname.equals("")) {
					errorLastname.setText("Το επώνυμο είναι υποχρεωτικό");
				}
				
				if (!inputLastname.equals("")) {
					errorLastname.setText("");
				}
			}
		});
		lastnameText.setBounds(85, 78, 222, 26);
		panel.add(lastnameText);
		lastnameText.setColumns(10);
		
		JLabel lastnameLabel = new JLabel("Επώνυμο");
		lastnameLabel.setBounds(12, 84, 71, 15);
		panel.add(lastnameLabel);
		lastnameLabel.setForeground(new Color(52, 101, 164));
		
		errorLastname = new JLabel("");
		errorLastname.setBounds(85, 101, 222, 26);
		panel.add(errorLastname);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Read user's input (Data binding)
				String inputFirstname = firstnameText.getText().trim();
				String inputLastname = lastnameText.getText().trim();
				
				// The following line is vulnerable to SQL Injection attack:
				// String sql = "INSERT INTO teachers (firstname, lastname) VALUES ('" + inputFirstname + "', '" + inputLastname + "')";
				
				// ------------------------------ Validation --------------------------------
				if(inputFirstname.equals("")) {
					errorFirstname.setText("Το όνομα είναι υποχρεωτικό.");
				}
				
				if (!inputFirstname.equals("")) {
					errorFirstname.setText("");
				}
				
				if(inputLastname.equals("")) {
					errorLastname.setText("Το επώνυμο είναι υποχρεωτικό");
				}
				
				if (!inputLastname.equals("")) {
					errorLastname.setText("");
				}
				
				if (inputFirstname.equals("") || inputLastname.equals("")) {
					return;
				}
				// --------------------------------------------------------------------------
				
				String sql = "INSERT INTO teachers (firstname, lastname) VALUES (?, ?)";
				
				try (Connection conn = DBUtil.getConnection();
					 PreparedStatement ps = conn.prepareStatement(sql)) {
					
					ps.setString(1, inputFirstname);	// Position #1
					ps.setString(2, inputLastname);		// Position #2
					
					// Execute the sql command and return the number of affected rows
					int n = ps.executeUpdate();
					
					// Pop up window
					JOptionPane.showMessageDialog(null, n + " record(s) inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
					
				} catch (SQLException e1) {
					// Error message window
					JOptionPane.showMessageDialog(null, "Insertion Error", "Error" ,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		insertBtn.setForeground(new Color(52, 101, 164));
		insertBtn.setBounds(130, 185, 117, 32);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenuFrame().setEnabled(true);
				Main.getTeachersInsertFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(52, 101, 164));
		closeBtn.setBounds(259, 185, 117, 32);
		contentPane.add(closeBtn);
	}
}
