package com.quangduong.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "role", nullable = false)
    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Role)) return false;
        Role role = (Role) o;
        return name.equals(role.name) && code.equals(role.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }
}