package gr.aueb.cf.schoolapp.validator;

import gr.aueb.cf.schoolapp.dto.BaseDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class used to validate student DTOs.
 */
public class StudentValidator {

    private StudentValidator() {
    }

    /**
     * Validates the fields of a given DTO instance.
     *
     * @param <T>   The type of the DTO, which extends `BaseDTO`.
     * @param dto   The DTO instance containing the fields to be validated.
     * @return      A map containing field names as keys and corresponding error messages as values.
     *              If no validation errors are found, the map will be empty.
     */
    public static <T extends BaseDTO> Map<String, String> validate(T dto) {
        final int MIN_LENGTH = 2;
        final int MAX_LENGTH = 32;

        String firstname = dto.getFirstname().trim();
        String lastname = dto.getLastname().trim();

        // If a field has an error, save the field and the corresponding message
        Map<String, String> errors = new HashMap<>();

        if (firstname.length() < MIN_LENGTH || firstname.length() > MAX_LENGTH) {
            errors.put("firstname", "Firstname should be between 2 and 32 characters.");
        }
        if (firstname.matches("^.*\\s+.*$")) {
            errors.put("firstname", "Firstname should not include whitespaces.");
        }

        if (lastname.length() < MIN_LENGTH || lastname.length() > MAX_LENGTH) {
            errors.put("lastname", "Lastname should be between 2 and 32 characters.");
        }
        if (lastname.matches("^.*\\s+.*$")) {
            errors.put("lastname", "Lastname should not include whitespaces.");
        }

        return errors;
    }
}
