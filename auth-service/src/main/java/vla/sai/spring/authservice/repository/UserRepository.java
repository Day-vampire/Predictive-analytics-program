package vla.sai.spring.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.authservice.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    Page<User> findAllByRole_Name(String roleName, Pageable pageable);
    Page<User> findAllByPerson_Country(String personCountry, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    void deleteById(Long id);
    void deleteByEmail(String email);

}