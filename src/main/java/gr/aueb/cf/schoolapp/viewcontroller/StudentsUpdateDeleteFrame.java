package gr.aueb.cf.schoolapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.validator.StudentValidator;

import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StudentsUpdateDeleteFrame extends JFrame {
	private static final IStudentDAO studentDAO = new StudentDAOImpl();
	private static final IStudentService studentService = new StudentServiceImpl(studentDAO);

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTable studentsTable;
	private final JTextField studentLnameSearchText;
	private final JTextField studentIdText;
	private final JTextField studentFnameText;
	private final JTextField studentLnameText;
	private final JLabel errorStudentFname;
	private final JLabel errorStudentLname;
	private final DefaultTableModel model;

	/**
	 * Create the frame.
	 */
	public StudentsUpdateDeleteFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				studentLnameSearchText.setText("");
				buildTable();
				cleanPanelTexts();
			}
			@Override
			public void windowActivated(WindowEvent e) {
				studentLnameSearchText.setText("");
				buildTable();
				cleanPanelTexts();
			}
		});
		setTitle("Ενημέρωση / Διαγραφή Εκπαιδευόμενου");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("eduv2.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 721, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		studentsTable = new JTable();
		studentsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				studentIdText.setText((String) model.getValueAt(studentsTable.getSelectedRow(), 0));
				studentFnameText.setText((String) model.getValueAt(studentsTable.getSelectedRow(), 1));
				studentLnameText.setText((String) model.getValueAt(studentsTable.getSelectedRow(), 2));
			}
		});
		studentsTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Κωδικός", "Όνομα", "Επώνυμο"}
		));
		studentsTable.setBounds(28, 44, 396, 441);
		contentPane.add(studentsTable);
		
		model = (DefaultTableModel) studentsTable.getModel();
		
		JScrollPane scrollPane = new JScrollPane(studentsTable);
		scrollPane.setBounds(28, 44, 396, 445);
		contentPane.add(scrollPane);
		
		JLabel studentLnameSearchLabel = new JLabel("Επώνυμο");
		studentLnameSearchLabel.setForeground(new Color(204, 0, 0));
		studentLnameSearchLabel.setBounds(28, 17, 70, 15);
		contentPane.add(studentLnameSearchLabel);
		
		studentLnameSearchText = new JTextField();
		studentLnameSearchText.setBounds(116, 12, 162, 25);
		contentPane.add(studentLnameSearchText);
		studentLnameSearchText.setColumns(10);
		
		JButton studentSearchBtn = new JButton("Αναζήτηση");
		studentSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTable();
			}
		});
		studentSearchBtn.setForeground(new Color(32, 74, 135));
		studentSearchBtn.setBounds(307, 12, 117, 25);
		contentPane.add(studentSearchBtn);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(436, 75, 263, 162);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel studentIdLabel = new JLabel("Κωδικός");
		studentIdLabel.setForeground(new Color(32, 74, 135));
		studentIdLabel.setBounds(12, 29, 59, 15);
		panel.add(studentIdLabel);
		
		JLabel studentFnameLabel = new JLabel("Όνομα");
		studentFnameLabel.setForeground(new Color(32, 74, 135));
		studentFnameLabel.setBounds(12, 73, 52, 15);
		panel.add(studentFnameLabel);
		
		JLabel studentLnameLabel = new JLabel("Επώνυμο");
		studentLnameLabel.setForeground(new Color(32, 74, 135));
		studentLnameLabel.setBounds(12, 117, 70, 15);
		panel.add(studentLnameLabel);
		
		studentIdText = new JTextField();
		studentIdText.setEnabled(false);
		studentIdText.setBounds(84, 27, 59, 19);
		panel.add(studentIdText);
		studentIdText.setColumns(10);
		
		studentFnameText = new JTextField();
		studentFnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname = studentFnameText.getText().trim();
				validateFirstname(inputFirstname);
			}
		});
		studentFnameText.setBounds(82, 71, 150, 19);
		panel.add(studentFnameText);
		studentFnameText.setColumns(10);
		
		studentLnameText = new JTextField();
		studentLnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname = studentLnameText.getText().trim();
				validateLastname(inputLastname);
			}
		});
		studentLnameText.setBounds(82, 115, 150, 19);
		panel.add(studentLnameText);
		studentLnameText.setColumns(10);
		
		errorStudentFname = new JLabel("");
		errorStudentFname.setForeground(new Color(204, 0, 0));
		errorStudentFname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorStudentFname.setBounds(84, 90, 148, 15);
		panel.add(errorStudentFname);
		
		errorStudentLname = new JLabel("");
		errorStudentLname.setForeground(new Color(204, 0, 0));
		errorStudentLname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorStudentLname.setBounds(84, 135, 148, 15);
		panel.add(errorStudentLname);
		
		JButton studentUpdateBtn = new JButton("Ενημέρωση");
		studentUpdateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Data Binding
				StudentUpdateDTO updateDTO = new StudentUpdateDTO(
					Integer.parseInt(studentIdText.getText().trim()),
					studentFnameText.getText().trim(),
					studentLnameText.getText().trim()
				);

				// Validation
				Map<String, String> errors = StudentValidator.validate(updateDTO);
				String firstnameMessage;
				String lastnameMessage;

				if (!errors.isEmpty()) {
					firstnameMessage = errors.getOrDefault("firstname", "");
					lastnameMessage = errors.getOrDefault("lastname" ,"");
					errorStudentFname.setText(firstnameMessage);
					errorStudentLname.setText(lastnameMessage);
					return;
				}

				try {
					int answer = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος/η;", "Ενημέρωση", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						Student student = studentService.updateStudent(updateDTO);
						StudentReadOnlyDTO readOnlyDTO = mapToStudentReadOnlyDTO(student);
						JOptionPane.showMessageDialog(null, "Student with id: " + readOnlyDTO.getId() + " was updated",
								"Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch(StudentNotFoundException | StudentDAOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Update error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		studentUpdateBtn.setForeground(new Color(32, 74, 135));
		studentUpdateBtn.setBounds(436, 249, 117, 35);
		contentPane.add(studentUpdateBtn);
		
		JButton studentDeleteBtn = new JButton("Διαγραφή");
		studentDeleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentIdText.getText().trim().isEmpty()) return;

				Integer inputId = Integer.parseInt(studentIdText.getText().trim());

				try {
					int answer = JOptionPane.showConfirmDialog(null, "Είστε σίγουρος/η;", "Διαγραφή", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						studentService.deleteStudent(inputId);
						JOptionPane.showMessageDialog(null,
								"Student with id: " + inputId +" was deleted", "Διαγραφή", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch(StudentNotFoundException | StudentDAOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Delete error" ,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		studentDeleteBtn.setForeground(new Color(32, 74, 135));
		studentDeleteBtn.setBounds(582, 249, 117, 35);
		contentPane.add(studentDeleteBtn);
		
		JButton closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getStudentsMenuFrame().setEnabled(true);
				Main.getStudentsUpdateDeleteFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(32, 74, 135));
		closeBtn.setBounds(582, 450, 117, 35);
		contentPane.add(closeBtn);
	}
	
	private void buildTable() {
		String lastnameSearchInput = studentLnameSearchText.getText().trim();
		List<Student> students;
		List<StudentReadOnlyDTO> readOnlyDTOS = new ArrayList<>();

		try {
			students = studentService.getStudentByLastname(lastnameSearchInput);

			for (Student student : students) {
				readOnlyDTOS.add(mapToStudentReadOnlyDTO(student));
			}

			addToModel(readOnlyDTOS);
		} catch(StudentDAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error" ,JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addToModel(List<StudentReadOnlyDTO> students) {
		Vector<String> vector;

		// Remove all table rows
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		// Put the output of SQL server to the model
		for(StudentReadOnlyDTO student : students) {
			vector = new Vector<>(3);
			vector.add(String.valueOf(student.getId()));
			vector.add(student.getFirstname());
			vector.add(student.getLastname());
			model.addRow(vector);
		}
	}

	private void cleanPanelTexts() {
		studentIdText.setText("");
		studentFnameText.setText("");
		studentLnameText.setText("");
		errorStudentFname.setText("");
		errorStudentLname.setText("");
	}
	
	private void validateFirstname(String inputFirstname) {		
		if(inputFirstname.isEmpty()) {
			errorStudentFname.setText("Το όνομα είναι υποχρεωτικό.");
		}

		if (inputFirstname.matches("^.*\\s+.*$")) {
			errorStudentFname.setText("Το όνομα δεν πρέπει να έχει κενά.");
		}

		if (!inputFirstname.isEmpty()) {
			errorStudentFname.setText("");
		}
	}
	
	private void validateLastname(String inputLastname) {		
		if(inputLastname.isEmpty()) {
			errorStudentLname.setText("Το επώνυμο είναι υποχρεωτικό");
		}

		if (inputLastname.matches("^.*\\s+.*$")) {
			errorStudentLname.setText("Το επώνυμο δεν πρέπει να έχει κενά.");
		}

		if (!inputLastname.isEmpty()) {
			errorStudentLname.setText("");
		}
	}

	private StudentReadOnlyDTO mapToStudentReadOnlyDTO(Student student) {
		return new StudentReadOnlyDTO(student.getId(), student.getFirstname(), student.getLastname());
	}
}
