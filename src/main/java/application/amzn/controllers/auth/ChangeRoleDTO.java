package application.amzn.controllers.auth;

import application.amzn.entities.UserRole;

public record ChangeRoleDTO(Long userId, UserRole role) {
}
