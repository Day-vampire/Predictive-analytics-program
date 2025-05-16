package vla.sai.spring.authservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vla.sai.spring.authservice.dto.PersonDto;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.entity.Person;

import java.util.Optional;

public interface PersonService {

    PersonDto save(PersonDto person);

    Optional<PersonDto> findPersonById(Long id);
    Optional<PersonDto> findByUser_Email(String userEmail);
    Page<PersonDto> findAllByCountry(String country, Pageable pageable);
    Page<PersonDto> findAllByLastName(String lastName, Pageable pageable);

    boolean existsPersonById(Long id);
    boolean existsByUser_Email(String userEmail);

    void deleteById(Long id);
}
