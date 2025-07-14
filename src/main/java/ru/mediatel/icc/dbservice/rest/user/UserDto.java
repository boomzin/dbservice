package ru.mediatel.icc.dbservice.rest.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.model.user.User;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String phone;
    private String email;
    private String tg;
    private String description;

    public UserDto(User user) {
        this(
                user.getId(),
                user.getPhone(),
                user.getEmail(),
                user.getTg(),
                user.getDescription()
        );
    }
}

