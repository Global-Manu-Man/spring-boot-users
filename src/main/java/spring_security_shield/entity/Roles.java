package spring_security_shield.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Roles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private String id;

    @Column(name = "name_role", nullable = true)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;


    //@JsonIgnore
    //@ManyToMany(mappedBy = "roles",  fetch = FetchType.EAGER)
    //private Set<Users> users = new HashSet<>();


}
