package gr.aueb.cf.schoolapp.dto;

public class StudentUpdateDTO extends BaseDTO {
    private Integer id;

    public StudentUpdateDTO() {
    }

    public StudentUpdateDTO(Integer id, String firstname, String lastname) {
        super(firstname, lastname);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" + id + getFirstname() + ", " + getLastname() + "}";
    }
}
