package gr.aueb.cf.schoolapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void defaultConstructorGettersAndSetters() {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        assertEquals(1, teacher.getId());

        teacher.setFirstname("M");
        assertEquals("M", teacher.getFirstname());

        teacher.setLastname("K");
        assertEquals("K", teacher.getLastname());
    }

    @Test
    void overloadedConstructorAndToString() {
        Teacher teacher = new Teacher(1, "Sponge", "Bob");
        assertEquals(1, teacher.getId());
        assertEquals("Sponge", teacher.getFirstname());
        assertEquals("Bob", teacher.getLastname());

        String expected = "id=1, firstname=Sponge, lastname=Bob";
        assertEquals(expected, teacher.toString());

    }
}