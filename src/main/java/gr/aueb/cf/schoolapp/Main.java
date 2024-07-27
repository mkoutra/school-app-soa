package gr.aueb.cf.schoolapp;

import gr.aueb.cf.schoolapp.viewcontroller.*;

import java.awt.EventQueue;

public class Main {
	private final static MainMenuFrame mainMenuFrame = new MainMenuFrame();
	private final static TeachersMenuFrame teachersMenuFrame = new TeachersMenuFrame();
	private final static TeachersInsertFrame teachersInsertFrame = new TeachersInsertFrame();
	private final static TeachersUpdateDeleteFrame teachersUpdateDeleteFrame = new TeachersUpdateDeleteFrame();
	private final static StudentsMenuFrame studentsMenuFrame = new StudentsMenuFrame();
	private final static StudentsInsertFrame studentsInsertFrame = new StudentsInsertFrame();
	private final static StudentsUpdateDeleteFrame studentsUpdateDeleteFrame = new StudentsUpdateDeleteFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainMenuFrame.setLocationRelativeTo(null);
					mainMenuFrame.setVisible(true);

					/*--- TEACHERS ---*/
					teachersMenuFrame.setLocationRelativeTo(null);
					teachersMenuFrame.setVisible(false);

					teachersInsertFrame.setLocationRelativeTo(null);
					teachersInsertFrame.setVisible(false);

					teachersUpdateDeleteFrame.setLocationRelativeTo(null);
					teachersUpdateDeleteFrame.setVisible(false);

					/*--- STUDENTS ---*/
					studentsMenuFrame.setLocationRelativeTo(null);
					studentsMenuFrame.setVisible(false);

					studentsInsertFrame.setLocationRelativeTo(null);
					studentsInsertFrame.setVisible(false);

					studentsUpdateDeleteFrame.setLocationRelativeTo(null);
					studentsUpdateDeleteFrame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static MainMenuFrame getMainMenuFrame() {
		return mainMenuFrame;
	}

	/*------------------ TEACHERS ------------------ */

	public static TeachersMenuFrame getTeachersMenuFrame() {
		return teachersMenuFrame;
	}

	public static TeachersInsertFrame getTeachersInsertFrame() {
		return teachersInsertFrame;
	}

	public static TeachersUpdateDeleteFrame getTeachersUpdateDeleteFrame() {
		return teachersUpdateDeleteFrame;
	}

	/*------------------ STUDENTS ------------------ */

	public static StudentsMenuFrame getStudentsMenuFrame() {
		return studentsMenuFrame;
	}

	public static StudentsInsertFrame getStudentsInsertFrame() {
		return studentsInsertFrame;
	}

	public static StudentsUpdateDeleteFrame getStudentsUpdateDeleteFrame() {
		return studentsUpdateDeleteFrame;
	}
}