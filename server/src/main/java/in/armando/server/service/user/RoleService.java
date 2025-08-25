package in.armando.server.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.io.role.RoleRequest;
import in.armando.server.io.role.RoleResponse;

@Service
public interface RoleService {
    RoleResponse createRole(RoleRequest request);

    RoleResponse getRoleById(Long id);

    List<RoleResponse> getAllRoles();

    void deleteRole(Long id);
}
