package vla.sai.spring.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.authservice.entity.Person;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person save(Person person);

    Optional<Person> findPersonById(Long id);
    Optional<Person> findByUser_Email(String userEmail);
    Page<Person> findAllByCountry(String country, Pageable pageable);
    Page<Person> findAllByLastName(String lastName, Pageable pageable);

    boolean existsPersonById(Long id);
    boolean existsByUser_Email(String userEmail);

    void deleteById(Long id);
}