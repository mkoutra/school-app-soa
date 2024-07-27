package gr.aueb.cf.schoolapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class TeachersInsertFrame extends JFrame {

	// Wiring
	private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

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
				errorFirstname.setText("");
				errorLastname.setText("");
				
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
		errorFirstname.setForeground(new Color(204, 0, 0));
		errorFirstname.setFont(new Font("Dialog", Font.BOLD, 10));
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
		errorLastname.setForeground(new Color(204, 0, 0));
		errorLastname.setFont(new Font("Dialog", Font.BOLD, 10));
		errorLastname.setBounds(85, 101, 222, 26);
		panel.add(errorLastname);
		
		JButton insertBtn = new JButton("Εισαγωγή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Teacher teacher;
				Map<String, String> errors;
				TeacherInsertDTO insertDTO = new TeacherInsertDTO();
				String firstnameMessage;
				String lastnameMessage;

				try {
					// Data binding
					insertDTO.setFirstname(firstnameText.getText().trim());
					insertDTO.setLastname(lastnameText.getText().trim());

					// Validation
					errors = TeacherValidator.validate(insertDTO);

					if (!errors.isEmpty()) {
						firstnameMessage = errors.getOrDefault("firstname", "");
						lastnameMessage = errors.getOrDefault("lastname", "");

						errorFirstname.setText(firstnameMessage);
						errorLastname.setText(lastnameMessage);

						return;
					}

					teacher = teacherService.insertTeacher(insertDTO);
					TeacherReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(teacher);

					JOptionPane.showMessageDialog(null, "Teacher with lastname: " + readOnlyDTO.getLastname(),
							"Insert teacher", JOptionPane.INFORMATION_MESSAGE);

				} catch (TeacherDAOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Insertion Error", "Error", JOptionPane.ERROR_MESSAGE);
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

	private TeacherReadOnlyDTO mapToReadOnlyDTO(Teacher teacher) {
		return new TeacherReadOnlyDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname());
	}
}
