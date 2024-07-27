package gr.aueb.cf.schoolapp.viewcontroller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TeachersUpdateDeleteFrame extends JFrame {

	private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

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
				lastnameSearchText.setText("");
				buildTable();
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
				errorFirstname.setText("");
				errorLastname.setText("");
			}
			@Override
			public void windowActivated(WindowEvent e) {
				lastnameSearchText.setText("");
				buildTable();	// Refresh after update / delete
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
				errorFirstname.setText("");
				errorLastname.setText("");
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
				Map<String, String> errors;
				String firstnameMessage;
				String lastnameMessage;
				Teacher teacher;

				if (idText.getText().trim().isEmpty()) return;

				try {
					// Data Binding
					TeacherUpdateDTO updateDTO = new TeacherUpdateDTO();
					updateDTO.setId(Integer.parseInt(idText.getText().trim()));
					updateDTO.setFirstname(firstnameText.getText().trim());
					updateDTO.setLastname(lastnameText.getText().trim());

					// Validation
					errors = TeacherValidator.validate(updateDTO);

					// If errors, assigns messages to UI
					if (!errors.isEmpty()) {
						firstnameMessage = errors.getOrDefault("firstname", "");
						lastnameMessage = errors.getOrDefault("lastname", "");
						errorFirstname.setText(firstnameMessage);
						errorLastname.setText(lastnameMessage);
						return;
					}

					// On validation success, call the update service
					teacher = teacherService.updateTeacher(updateDTO);

					// Results mapped to ReadOnlyDTO
					TeacherReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(teacher);

					// Feedback
					JOptionPane.showMessageDialog(null, "Teacher with lastname: " + readOnlyDTO.getLastname() + " was updated",
							"Update teacher", JOptionPane.INFORMATION_MESSAGE);
				} catch (TeacherDAOException | TeacherNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error" ,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(new Color(52, 101, 164));
		updateBtn.setBounds(434, 263, 114, 43);
		contentPane.add(updateBtn);
		
		deleteBtn = new JButton("Διαγραφή");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response;

				try {
					if (idText.getText().trim().isEmpty()) return;

					int inputId = Integer.parseInt(idText.getText().trim());	// Data Binding

					response = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος;", "Warning", JOptionPane.YES_NO_OPTION);

					if (response == JOptionPane.YES_OPTION) {
						teacherService.deleteTeacher(inputId);
						JOptionPane.showMessageDialog(null, "Teacher with id:" + inputId + " was deleted successfully",
								"Delete", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch(TeacherNotFoundException | TeacherDAOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error" , JOptionPane.ERROR_MESSAGE);
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
		errorFirstname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorFirstname.setForeground(new Color(204, 0, 0));
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
		errorLastname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorLastname.setForeground(new Color(204, 0, 0));
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
	
	private void buildTable() {
		Vector<String> vector;
		List<TeacherReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
		TeacherReadOnlyDTO readOnlyDTO;

		try {
			String searchStr = lastnameSearchText.getText().trim();

			List<Teacher> teachers = teacherService.getTeacherByLastname(searchStr);

			// Fill the array with readOnlyDTOs
			for (Teacher teacher : teachers) {
				readOnlyDTO = mapToReadOnlyDTO(teacher);
				readOnlyDTOS.add(readOnlyDTO);
			}

			// Remove rows from model
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}

			for (TeacherReadOnlyDTO teacherReadOnlyDTO : readOnlyDTOS) {
				vector = new Vector<>(3);
				vector.add(String.valueOf(teacherReadOnlyDTO.getId()));
				vector.add(teacherReadOnlyDTO.getFirstname());
				vector.add(teacherReadOnlyDTO.getLastname());

				model.addRow(vector);
			}
		} catch (TeacherDAOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error" ,JOptionPane.ERROR_MESSAGE);
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

	private TeacherReadOnlyDTO mapToReadOnlyDTO(Teacher teacher) {
		return new TeacherReadOnlyDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname());
	}
}
