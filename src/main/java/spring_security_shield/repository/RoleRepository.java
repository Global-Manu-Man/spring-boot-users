package spring_security_shield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_security_shield.entity.Roles;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {
    Roles findByName(String name);
    Optional<Roles> findById(String id);
}