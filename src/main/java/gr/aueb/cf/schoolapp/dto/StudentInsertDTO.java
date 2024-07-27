package gr.aueb.cf.schoolapp.dto;

public class StudentInsertDTO extends BaseDTO {
    public StudentInsertDTO() {
    }

    public StudentInsertDTO(String firstname, String lastname) {
        super(firstname, lastname);
    }

    @Override
    public String toString() {
        return this.getFirstname() + ", " + this.getLastname();
    }
}
