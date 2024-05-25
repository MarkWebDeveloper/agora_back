package de.stella.agora_web.controller;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.login.dto.ShowPermissionDTO;
import de.stella.agora_web.roles.dto.SavePermissionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<ShowPermissionDTO>> findAll(Pageable pageable) {
        Page<ShowPermissionDTO> permissions = permissionService.findAll(pageable);

        if (permissions.hasContent()) {
            return ResponseEntity.ok(permissions);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermissionDTO> findOneById(@PathVariable Long permissionId) {
        Optional<ShowPermissionDTO> permission = permissionService.findOneById(permissionId);

        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<ShowPermissionDTO> createOne(@RequestBody @Valid SavePermissionDTO savePermission) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createOne(savePermission));
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ShowPermissionDTO> deleteOne(@PathVariable Long permissionId) {
        return ResponseEntity.status(HttpStatus.OK).body(permissionService.deleteOne(permissionId));
    }
}