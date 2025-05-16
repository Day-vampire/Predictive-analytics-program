package vla.sai.spring.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.authservice.entity.UserImageData;

import java.util.Optional;
@Repository
public interface UserImageDataRepository extends JpaRepository<UserImageData, Long> {
    UserImageData save(UserImageData userImageData);
    Optional<UserImageData> findByUser_Email(String userEmail);
    Optional<UserImageData> findByUser_Id(Long userId);
    Optional<UserImageData> findByName(String name);
}
