package mate.academy.mybookshop.repository;

import mate.academy.mybookshop.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
