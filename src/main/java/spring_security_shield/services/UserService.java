package spring_security_shield.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import spring_security_shield.dto.UserRoleDTO;
import spring_security_shield.entity.Users;


public interface UserService {

    void createUser(Users users);
    boolean findUserByUsername(String username);
    UserRoleDTO findUserById(String id);
    Page<Users> findAllUsers(Pageable pageable);
    void deleteUserById(String id);
    void updateUserActiveStatus(String id, boolean active);
    void updateUser(String id, Users updatedUser);

}
