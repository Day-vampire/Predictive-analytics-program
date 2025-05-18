package vla.sai.spring.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    boolean existsByEmail(String email);
    boolean existsById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userImageData.id = :imageId WHERE u.id = :userId")
    void updateUserImage(@Param("userId") Long userId, @Param("imageId") Long imageId);
}