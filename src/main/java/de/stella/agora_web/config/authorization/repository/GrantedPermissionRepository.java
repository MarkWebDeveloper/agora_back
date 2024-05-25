package de.stella.agora_web.config.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.stella.agora_web.config.authorization.model.GrantedPermission;


public interface GrantedPermissionRepository extends JpaRepository<GrantedPermission, Long> {

}