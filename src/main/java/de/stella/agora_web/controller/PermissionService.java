package de.stella.agora_web.controller;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.stella.agora_web.login.dto.ShowPermissionDTO;
import de.stella.agora_web.roles.dto.SavePermissionDTO;

public interface PermissionService {
    Page<ShowPermissionDTO> findAll(Pageable pageable);

    Optional<ShowPermissionDTO> findOneById(Long permissionId);

    ShowPermissionDTO createOne(SavePermissionDTO savePermission);

    ShowPermissionDTO deleteOne(Long permissionId);
}