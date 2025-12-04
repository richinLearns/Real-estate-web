package ca.sheridancollege.richin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.sheridancollege.richin.model.Property;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class PropertyValidationTest {
	private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldAcceptValidProperty() {
        Property p = new Property(null, "Valid Address", 100000.0, "Active");
        Set<ConstraintViolation<Property>> violations = validator.validate(p);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldRejectBlankAddress() {
        Property p = new Property(null, "", 100000.0, "Active");
        Set<ConstraintViolation<Property>> violations = validator.validate(p);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Address is required")));
    }

    @Test
    void shouldRejectNegativePrice() {
        Property p = new Property(null, "Good", -50.0, "Active");
        Set<ConstraintViolation<Property>> violations = validator.validate(p);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Price must be > 0")));
    }

}
