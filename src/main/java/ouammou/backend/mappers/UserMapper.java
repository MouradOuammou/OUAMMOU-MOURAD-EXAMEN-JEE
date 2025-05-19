package ouammou.backend.mappers;


import org.springframework.stereotype.Component;
import ouammou.backend.dtos.UserDTO;
import ouammou.backend.entites.User;


@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());

        if (user.getClient() != null) {
            userDTO.setClientId(user.getClient().getId());
        }

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());

        return user;
    }
}
