package mate.academy.mybookshop.repository;

import java.util.Optional;
import mate.academy.mybookshop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
