package ca.sheridancollege.richin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ca.sheridancollege.richin.model.Property;
import ca.sheridancollege.richin.repository.PropertyRepository;
import ca.sheridancollege.richin.service.PropertyService;

@SpringBootTest
@Transactional
public class PropertyServiceTest {
	@Autowired
    private PropertyService service;

    @Autowired
    private PropertyRepository repo;

    @Test
    void shouldSaveAndRetrieveProperty() {
        Property p = new Property(null, "123 Main St", 350000.0, "For Sale");
        Property saved = service.save(p);

        assertNotNull(saved.getId());
        assertEquals("123 Main St", saved.getAddress());

        Property found = service.getById(saved.getId());
        assertEquals(350000.0, found.getPrice());
    }

    @Test
    void shouldGetAllProperties() {
        repo.save(new Property(null, "Test 1", 100000.0, "Sold"));
        repo.save(new Property(null, "Test 2", 200000.0, "Pending"));

        List<Property> all = service.getAll();
        assertTrue(all.size() >= 2);
    }

    @Test
    void shouldDeleteProperty() {
        Property p = repo.save(new Property(null, "To Delete", 999999.0, "Active"));
        service.delete(p.getId());

        assertNull(service.getById(p.getId()));
    }

}
