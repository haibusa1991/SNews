package com.snews.server.services.userRole;

import com.snews.server.entities.UserRole;
import com.snews.server.enumeration.UserRoleEnum;

public interface UserRoleService {
    UserRole getUserRole(UserRoleEnum userRoleEnum);
}
