package com.snews.server.services.userRole;

import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.UserRoleEnum;

public interface UserRoleService {
    UserRoleEntity getUserRole(UserRoleEnum userRoleEnum);

    void initRoles();
}
