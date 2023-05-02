package org.sanchez.corcoles.ana.pruebasconcepto.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_in_role")
public class UserInRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public UserInRole() {
    }

    public UserInRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInRole userInRole = (UserInRole) o;
        return id.equals(userInRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
