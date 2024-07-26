package gr.aueb.cf.schoolapp.dto;

public class StudentReadOnlyDTO extends BaseDTO {
    private Integer id;

    public StudentReadOnlyDTO() {
    }

    public StudentReadOnlyDTO(Integer id, String firstname, String lastname) {
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
        return "{" + id + ", "+ getFirstname() + ", " + getLastname() + "}";
    }
}
