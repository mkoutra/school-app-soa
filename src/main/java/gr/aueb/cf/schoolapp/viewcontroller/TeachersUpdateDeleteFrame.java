package gr.aueb.cf.schoolapp.viewcontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Toolkit;

public class TeachersUpdateDeleteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable teachersTable;
	private DefaultTableModel model = new DefaultTableModel();
	private JLabel lastnameSearchLabel;
	private JTextField lastnameSearchText;
	private JButton btnSearch;
	private JLabel idLabel;
	private JTextField idText;
	private JLabel firstnameLabel;
	private JTextField firstnameText;
	private JLabel lastnameLabel;
	private JTextField lastnameText;
	private JLabel errorFirstname;
	private JLabel errorLastname;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JPanel panel;
	private JButton closeBtn;
	
	
	/**
	 * Create the frame.
	 */
	public TeachersUpdateDeleteFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		setTitle("Ενημέρωση / Διαγραφή Εκπαιδευτή");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				buildTable();
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
			@Override
			public void windowActivated(WindowEvent e) {
				buildTable();	// Refresh after update / delete
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 695, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		teachersTable = new JTable();
		teachersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idText.setText((String) model.getValueAt(teachersTable.getSelectedRow(), 0));
				firstnameText.setText((String) model.getValueAt(teachersTable.getSelectedRow(), 1));
				lastnameText.setText((String) model.getValueAt(teachersTable.getSelectedRow(), 2));
			}
		});
		teachersTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Κωδικός", "Όνομα", "Επώνυμο"}
		));
		
		model = (DefaultTableModel) teachersTable.getModel();
		
		teachersTable.setBounds(52, 12, 336, 437);
		contentPane.add(teachersTable);
		
		JScrollPane scrollPane = new JScrollPane(teachersTable);
		scrollPane.setBounds(52, 50, 370, 437);
		contentPane.add(scrollPane);
		
		lastnameSearchLabel = new JLabel("Επώνυμο");
		lastnameSearchLabel.setForeground(new Color(204, 0, 0));
		lastnameSearchLabel.setBounds(52, 17, 70, 15);
		contentPane.add(lastnameSearchLabel);
		
		lastnameSearchText = new JTextField();
		lastnameSearchText.setBounds(127, 15, 151, 19);
		contentPane.add(lastnameSearchText);
		lastnameSearchText.setColumns(10);
		
		btnSearch = new JButton("Αναζήτηση");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTable();
			}
		});
		btnSearch.setForeground(new Color(52, 101, 164));
		btnSearch.setBounds(300, 15, 122, 19);
		contentPane.add(btnSearch);
		
		idLabel = new JLabel("Κωδικός");
		idLabel.setForeground(new Color(52, 101, 164));
		idLabel.setBounds(450, 100, 61, 15);
		contentPane.add(idLabel);
		
		firstnameLabel = new JLabel("Όνομα");
		firstnameLabel.setForeground(new Color(52, 101, 164));
		firstnameLabel.setBounds(460, 142, 51, 15);
		contentPane.add(firstnameLabel);
		
		lastnameLabel = new JLabel("Επώνυμο");
		lastnameLabel.setForeground(new Color(52, 101, 164));
		lastnameLabel.setBounds(440, 187, 71, 15);
		contentPane.add(lastnameLabel);
		
		updateBtn = new JButton("Ενημέρωση");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Data Binding
				int inputId = Integer.parseInt(idText.getText().trim());
				String inputFirstname = firstnameText.getText().trim();
				String inputLastname = lastnameText.getText().trim();
				
				// ------------------------------ Validation --------------------------------
				validateFirstname(inputFirstname);
				validateLastname(inputLastname);
				
				if (inputFirstname.isEmpty() || inputLastname.isEmpty()) {
					return;
				}
				// --------------------------------------------------------------------------
				String sql = "UPDATE teachers SET firstname = ?, lastname = ? WHERE id = ?";
				
				try (Connection conn = DBUtil.getConnection();
					 PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1 , inputFirstname);
					ps.setString(2 , inputLastname);
					ps.setInt(3 , inputId);
					
					int answer = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος/η;", "Ενημέρωση", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						int rowsAffected = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, rowsAffected + "γραμμή/ες ενημερώθηκαν", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Update Error", "Error" ,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(new Color(52, 101, 164));
		updateBtn.setBounds(434, 263, 114, 43);
		contentPane.add(updateBtn);
		
		deleteBtn = new JButton("Διαγραφή");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM teachers WHERE id = ?";
				
				try (Connection conn = DBUtil.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql)){
					
					int inputId = Integer.parseInt(idText.getText().trim());	// Data binding
					
					ps.setInt(1, inputId);
					
					int answer = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος/η;", "Διαγραφή", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						int rowsAffected = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, rowsAffected + "γραμμή/ες διαγράφηκαν", "Διαγραφή", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
				} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "Delete Error", "Error" ,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(new Color(52, 101, 164));
		deleteBtn.setBounds(569, 263, 114, 43);
		contentPane.add(deleteBtn);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(434, 75, 251, 164);
		contentPane.add(panel);
		panel.setLayout(null);
		
		firstnameText = new JTextField();
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname = firstnameText.getText().trim(); 
				validateFirstname(inputFirstname);
				
				
			}
		});
		firstnameText.setBounds(79, 69, 160, 19);
		panel.add(firstnameText);
		firstnameText.setColumns(10);
		
		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(239, 41, 41));
		errorFirstname.setBounds(79, 85, 160, 19);
		panel.add(errorFirstname);
		
		lastnameText = new JTextField();
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname = lastnameText.getText().trim();
				validateLastname(inputLastname);

			}
		});
		lastnameText.setBounds(79, 114, 160, 19);
		panel.add(lastnameText);
		lastnameText.setColumns(10);
		
		errorLastname = new JLabel("");
		errorLastname.setForeground(new Color(239, 41, 41));
		errorLastname.setBounds(79, 133, 160, 19);
		panel.add(errorLastname);
		
		idText = new JTextField();
		idText.setBounds(79, 24, 114, 19);
		panel.add(idText);
		idText.setEditable(false);
		idText.setColumns(10);
		
		closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersMenuFrame().setEnabled(true);
				Main.getTeachersUpdateDeleteFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(52, 101, 164));
		closeBtn.setBounds(569, 444, 114, 43);
		contentPane.add(closeBtn);
	}
	
	// When UpdateDelete frame opens, table must be built.
	private void buildTable() {
		Vector<String> vector;
		
		String sql = "SELECT id, firstname, lastname FROM teachers WHERE lastname LIKE ?";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setString(1, lastnameSearchText.getText().trim() + "%");
			
			ResultSet rs = ps.executeQuery();
			
			// Clear model means clear table (because it is MVVM model-view-view-model)
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			
			// Initially, rs shows before the first element of the table. rs is called "cursor"
			while(rs.next()) {
				vector = new Vector<>(3);	// Vector with size 3
				vector.add(rs.getString("id"));
				vector.add(rs.getString("firstname"));
				vector.add(rs.getString("lastname"));
				
				model.addRow(vector);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Search Error", "Error" ,JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void validateFirstname(String inputFirstname) {		
		inputFirstname = firstnameText.getText().trim(); 
		if(inputFirstname.equals("")) {
			errorFirstname.setText("Το όνομα είναι υποχρεωτικό.");
		}
		
		if (!inputFirstname.equals("")) {
			errorFirstname.setText("");
		}
	}
	
	private void validateLastname(String inputLastname) {		
		inputLastname = lastnameText.getText().trim();
		
		if(inputLastname.equals("")) {
			errorLastname.setText("Το επώνυμο είναι υποχρεωτικό");
		}
		
		if (!inputLastname.equals("")) {
			errorLastname.setText("");
		}
	}
}
