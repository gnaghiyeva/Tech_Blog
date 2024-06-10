package org.example.techblog.services;

import org.example.techblog.dtos.roledtos.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getRoles();
}
