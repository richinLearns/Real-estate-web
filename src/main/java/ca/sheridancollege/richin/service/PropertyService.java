package ca.sheridancollege.richin.service;

import java.util.List;


import org.springframework.stereotype.Service;

import ca.sheridancollege.richin.model.Property;
import ca.sheridancollege.richin.repository.PropertyRepository;
import java.util.Optional;

@Service
public class PropertyService {
	private final PropertyRepository repo;

    public PropertyService(PropertyRepository repo) {
        this.repo = repo;
    }

    public List<Property> getAll() {
        return repo.findAll();
    }

    public Property getById(Long id) {
        Optional<Property> o = repo.findById(id);
        return o.orElse(null);
    }

    public Property save(Property property) {
        return repo.save(property);
    }

    public void delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }
}
