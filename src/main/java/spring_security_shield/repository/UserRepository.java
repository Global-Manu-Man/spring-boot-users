package spring_security_shield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_security_shield.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

      //findByUsername(String username);
    Optional<Users> findByUsername(String username);

     //findByEmail(String email);
    Optional<Users> findByEmail(String email);

    // Método para verificar si un nombre de usuario ya existe
    boolean existsByUsername(String username);

    // Método para verificar si un correo electrónico ya existe
    boolean existsByEmail(String email);


}
