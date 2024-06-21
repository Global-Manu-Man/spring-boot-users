package spring_security_shield.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import spring_security_shield.constants.StaticVariables;
import spring_security_shield.dto.UserRoleDTO;
import spring_security_shield.mappers.UserRoleRowMapper;
import spring_security_shield.entity.Roles;
import spring_security_shield.mappers.UserRowMapper;
import spring_security_shield.exception.ResourceNotFoundException;
import spring_security_shield.repository.RoleRepository;
import spring_security_shield.repository.UserRepository;
import spring_security_shield.services.UserService;
import spring_security_shield.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public boolean findUserByUsername(String username) {
        return false;
    }



    @Override
    public void createUser(Users users) {
        validateUser(users);
        encryptPassword(users);
        setUserDefaults(users);

        saveUser(users);

        assignRole(users);
    }


    private void validateUser(Users users) {
        if (userRepository.findByUsername(users.getUsername()).isPresent()) {
            throw new RuntimeException(StaticVariables.USERNAME_EXISTS);
        }

        if (userRepository.findByEmail(users.getEmail()).isPresent()) {
            throw new RuntimeException(StaticVariables.EMAIL_EXISTS);
        }

        if (users.getLastName() == null || users.getLastName().isEmpty()) {
            throw new RuntimeException(StaticVariables.LAST_NAME_NULL_OR_EMPTY);
        }
    }
/*    private void encryptPassword(Users users) {
        String saltedPassword = "SALT" + users.getPassword();
        String encryptedPassword = DigestUtils.sha256Hex(saltedPassword);
        users.setPassword(encryptedPassword);
    }*/

   private void encryptPassword(Users users) {
       String encryptedPassword = passwordEncoder.encode(users.getPassword());
       users.setPassword(encryptedPassword);
   }
    private void setUserDefaults(Users users) {
        users.setId(UUID.randomUUID().toString());
        users.setActivo(false);
        users.setFechaCreacion(new Date());
        users.setFechaModificacion(new Date());
        //users.setPassword(passwordEncoder.encode(users.getPassword()));
    }
    private Users saveUser(Users users) {
        try {
            return userRepository.save(users);
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user", e);
        }
    }
    private void assignRole(Users users) {
        Roles role;
        if (users.getRoleId() == null) {
            role = roleRepository.findById(String.valueOf(2L)).orElseThrow(() -> new RuntimeException(StaticVariables.DEFAULT_ROLE_NOT_FOUND));
        } else {
            role = switch (users.getRoleId().intValue()) {
                case 1 -> roleRepository.findById(String.valueOf(1L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                case 2 -> roleRepository.findById(String.valueOf(2L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                case 3 -> roleRepository.findById(String.valueOf(3L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                case 4 -> roleRepository.findById(String.valueOf(4L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                case 5 -> roleRepository.findById(String.valueOf(5L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                case 6 -> roleRepository.findById(String.valueOf(6L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                case 7 -> roleRepository.findById(String.valueOf(7L)).orElseThrow(() -> new RuntimeException(StaticVariables.INVALID_ROLE));
                default -> throw new RuntimeException(StaticVariables.INVALID_ROLE);
            };
        }

        users.setNameRole(role.getName());
        logger.info("Asignando rol con ID: " + role.getId() + " al usuario con ID: " + users.getId());

        try {
            String insertUserRoleSql = "INSERT INTO user_roles (id_user, id_roles, name_role) VALUES (?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(insertUserRoleSql, users.getId(), role.getId(), role.getName());
            if (rowsAffected > 0) {
                logger.info("Inserción en user_roles exitosa para el usuario con ID: " + users.getId());
            } else {
                logger.error("Fallo en la inserción en user_roles para el usuario con ID: " + users.getId());
            }
        } catch (Exception e) {
            logger.error("Error al insertar en user_roles: " + e.getMessage());
            throw new RuntimeException("Error al insertar en user_roles", e);
        }
    }


    @Override
    public UserRoleDTO findUserById(String id) {
        String sql = "SELECT u.id_user, u.user_name, u.last_name, u.password, u.email, u.active, " +
                "u.creation_date, u.modification_date, ur.id_roles, ur.name_role " +
                "FROM users u " +
                "INNER JOIN user_roles ur ON u.id_user = ur.id_user " +
                "WHERE u.id_user = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRoleRowMapper());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public Page<Users> findAllUsers(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int offset = currentPage * pageSize;

        String query = "SELECT u.id_user, u.user_name, u.last_name, u.email, u.active,u.password, u.creation_date, u.modification_date, ur.name_role " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.id_user = ur.id_user " +
                "ORDER BY u.user_name " +
                "LIMIT :limit OFFSET :offset";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("limit", pageSize);
        parameters.addValue("offset", offset);

        List<Users> users = namedParameterJdbcTemplate.query(query, parameters, new UserRowMapper());

        String countQuery = "SELECT COUNT(*) FROM users";
        int totalElements = namedParameterJdbcTemplate.queryForObject(countQuery, new MapSqlParameterSource(), Integer.class);

        return new PageImpl<>(users, pageable, totalElements);
    }

    @Override
    public void deleteUserById(String id) {
        // Verificar si el usuario existe
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        try {
            // Eliminar el registro en la tabla user_roles
            String deleteUserRolesSql = "DELETE FROM user_roles WHERE id_user = ?";
            int rowsAffectedUserRoles = jdbcTemplate.update(deleteUserRolesSql, id);
            logger.info("Deleted " + rowsAffectedUserRoles + " rows from user_roles for user ID: " + id);

            // Eliminar el registro en la tabla users
            String deleteUserSql = "DELETE FROM users WHERE id_user = ?";
            int rowsAffectedUsers = jdbcTemplate.update(deleteUserSql, id);
            logger.info("Deleted " + rowsAffectedUsers + " rows from users for user ID: " + id);

        } catch (Exception e) {
            logger.error("Error al eliminar el usuario con ID: " + id, e);
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }

    @Override
    public void updateUserActiveStatus(String id, boolean active) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setActivo(active);
        userRepository.save(user);
    }

    @Override
    public void updateUser(String id, Users updatedUser) {
        Users existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setLastName(updatedUser.getLastName());
        userRepository.save(existingUser);
    }






}