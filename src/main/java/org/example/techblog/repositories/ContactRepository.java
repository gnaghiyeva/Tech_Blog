package org.example.techblog.repositories;

import org.example.techblog.models.Contact;
import org.example.techblog.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByEmail(String email);
}
