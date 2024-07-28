package gr.aueb.cf.schoolapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void defaultConstructorAndSettersAndGetters() {
        Student student = new Student();

        student.setId(123);
        assertEquals(123, student.getId());

        student.setFirstname("James");
        assertEquals("James", student.getFirstname());

        student.setLastname("Bond");
        assertEquals("Bond", student.getLastname());

    }

    @Test
    void overloadedConstructorAndToString() {
        Student student = new Student(10, "Luka", "Modric");
        assertEquals(10, student.getId());
        assertEquals("Luka", student.getFirstname());
        assertEquals("Modric", student.getLastname());

        String expectedString = "id=10, firstname=Luka, lastname=Modric";
        assertEquals(expectedString, student.toString());
    }
}