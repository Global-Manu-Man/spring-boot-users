package spring_security_shield.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private String idUser;
    private String username;
    private String lastName;

    @JsonIgnore
    private String password;

    private String email;
    private Boolean active;
    private Date creationDate;
    private Date modificationDate;
    private String idRoles;
    private String nameRole;

}