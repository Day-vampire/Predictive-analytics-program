package vla.sai.spring.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vla.sai.spring.authservice.dto.PersonDto;
import vla.sai.spring.authservice.entity.Person;
import vla.sai.spring.authservice.mapper.PersonMapper;
import vla.sai.spring.authservice.repository.PersonRepository;
import vla.sai.spring.authservice.service.PersonService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonDto save(PersonDto person) {
        Person personEntity = personMapper.toEntity(person);
        return personMapper.toDto(personRepository.save(personEntity));
    }

    @Override
    public Optional<PersonDto> findPersonById(Long id) {
        return personRepository.findById(id).map(personMapper::toDto);
    }

    @Override
    public Optional<PersonDto> findByUser_Email(String userEmail) {
        return personRepository.findByUser_Email(userEmail).map(personMapper::toDto);
    }

    @Override
    public Page<PersonDto> findAllByCountry(String country, Pageable pageable) {
        return personRepository.findAllByCountry(country, pageable).map(personMapper::toDto);
    }

    @Override
    public Page<PersonDto> findAllByLastName(String lastName, Pageable pageable) {
        return personRepository.findAllByLastName(lastName, pageable).map(personMapper::toDto);
    }

    @Override
    public boolean existsPersonById(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public boolean existsByUser_Email(String userEmail) {
        return personRepository.existsByUser_Email(userEmail);
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
