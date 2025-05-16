package vla.sai.spring.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.authservice.entity.Role;
import vla.sai.spring.authservice.entity.User;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role save(Role role);
    boolean existsRoleById(Long id);
    boolean existsRoleByName(String roleName);

    Optional<Role> findRoleById(Long id);
    Optional<Role> findRoleByName(String roleName);

    void deleteById(Long id);
}