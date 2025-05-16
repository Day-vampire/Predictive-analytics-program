package vla.sai.spring.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import vla.sai.spring.authservice.dto.PersonDto;
import vla.sai.spring.authservice.service.PersonService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping(path = "/save")
    @Operation(summary = "Сохранение данных пользователя ")
    public PersonDto save(PersonDto personDto) {
        return personService.save(personDto);
    }

    @GetMapping(path = "/find-by-id")
    @Operation(summary = "Получение данных пользователя по id")
    public Optional<PersonDto> findPersonById(Long id) {
        return personService.findPersonById(id);
    }

    @GetMapping(path = "/find-by-email")
    @Operation(summary = "Получение данных пользователя по email")
    public Optional<PersonDto> findPersonByEmail(String email) {
        return personService.findByUser_Email(email);
    }

    @GetMapping(path = "/find-by-last-name")
    @Operation(summary = "Получение данных пользователей по last-name")
    public Page<PersonDto> findAllByLastName(String lastName, Pageable pageable) {
        return personService.findAllByLastName(lastName, pageable);
    }

    @GetMapping(path = "/find-by-country")
    @Operation(summary = "Получение данных пользователя по country")
    public Page<PersonDto> findAllByCountry(String country, Pageable pageable) {
        return personService.findAllByCountry(country, pageable);
    }

    @DeleteMapping(path = "/delete")
    @Operation(summary = "Удаление данных пользователя ")
    public void delete(Long id) {
        personService.deleteById(id);
    }
}
