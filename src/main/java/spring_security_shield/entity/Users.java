package spring_security_shield.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "id_user", updatable = false, nullable = false)
    private String id;


    @Column(name = "user_name")
    private String username;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "password")
    private String password;


    @Column(name = "email")
    private String email;


    @Column(name = "active")
    private boolean activo;

    @Transient
    @JsonIgnore
    private Long roleId;

    @Transient
    private String nameRole;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date fechaCreacion;


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date", nullable = false)
    private Date fechaModificacion;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_roles")}
    )

    private Set<Roles> roles = new HashSet<>();

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Users() {
    }


    public Users(Date fechaCreacion, String id, String username, String lastName, String password, String email, boolean activo, Long roleId, String nameRole, Date fechaModificacion, Set<Roles> roles) {
        this.fechaCreacion = fechaCreacion;
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.activo = activo;
        this.roleId = roleId;
        this.nameRole = nameRole;
        this.fechaModificacion = fechaModificacion;
        this.roles = roles;
    }
}

