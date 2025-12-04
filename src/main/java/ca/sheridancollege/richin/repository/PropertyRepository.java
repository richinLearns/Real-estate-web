package ca.sheridancollege.richin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.richin.model.Property;

public interface PropertyRepository extends JpaRepository <Property, Long> {

}
