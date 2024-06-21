package spring_security_shield.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String lastName;
    private String password;
    private String email;
    private boolean active;
    private Date creationDate;
    private Date modificationDate;

}