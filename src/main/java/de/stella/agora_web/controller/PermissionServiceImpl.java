package de.stella.agora_web.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.PageImpl;

import de.stella.agora_web.config.authorization.model.GrantedPermission;
import de.stella.agora_web.config.authorization.model.Operation;
import de.stella.agora_web.config.authorization.repository.GrantedPermissionRepository;
import de.stella.agora_web.config.authorization.repository.OperationRepository;
import de.stella.agora_web.exception.ObjectNotFoundException;
import de.stella.agora_web.login.dto.ShowPermissionDTO;
import de.stella.agora_web.roles.dto.SavePermissionDTO;
import de.stella.agora_web.roles.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final GrantedPermissionRepository grantedPermissionRepository;
    private final OperationRepository operationRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<ShowPermissionDTO> findAll(Pageable pageable) {
        Page<GrantedPermission> permissions = grantedPermissionRepository.findAll(pageable);

        List<ShowPermissionDTO> showPermissions = permissions.stream().map(PermissionServiceImpl::mapEntityToShowDto)
                .toList();

        return new PageImpl<>(showPermissions, pageable, permissions.getTotalElements());
    }

    @Override
    public Optional<ShowPermissionDTO> findOneById(Long permissionId) {
        return grantedPermissionRepository.findById(permissionId).map(PermissionServiceImpl::mapEntityToShowDto);
    }

    @Override
    public ShowPermissionDTO createOne(SavePermissionDTO savePermission) {
        Operation operation = operationRepository.findByName(savePermission.getOperation())
                .orElseThrow(() -> new ObjectNotFoundException("Operation not found"));

        ShowPermissionDTO role = roleRepository.findByName(savePermission.getRole()).
                orElseThrow(() -> new ObjectNotFoundException("Role not found"));

        GrantedPermission grantedPermission = new GrantedPermission();
        grantedPermission.setOperation(operation);
        grantedPermission.setRole(role);

        grantedPermissionRepository.save(grantedPermission);

        return mapEntityToShowDto(grantedPermission);

    }

    @Override
    public ShowPermissionDTO deleteOne(Long permissionId) {
        GrantedPermission grantedPermission = grantedPermissionRepository.findById(permissionId)
                .orElseThrow(() -> new ObjectNotFoundException("Permission not found"));
        grantedPermissionRepository.delete(grantedPermission);
        return mapEntityToShowDto(grantedPermission);
    }
    private static ShowPermissionDTO mapEntityToShowDto(GrantedPermission grantedPermission) {
        return ShowPermissionDTO.builder()
                .id(grantedPermission.getId())
                .operation(grantedPermission.getOperation().getName())
                .httpMethod(grantedPermission.getOperation().getHttpMethod())
                .module(grantedPermission.getOperation().getModule().getName())
                .role(grantedPermission.getRole().getName()).build();
    }
}