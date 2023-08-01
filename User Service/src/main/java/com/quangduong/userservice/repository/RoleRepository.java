package com.quangduong.userservice.repository;

import com.quangduong.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findOneByCode(String code);
}
