package co.berako.api.handlers.weather.dto;

import co.berako.model.weather.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {
    private String email;
    private Date timestamp;

    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .timestamp(user.getTimestamp())
                .build();
    }
}
