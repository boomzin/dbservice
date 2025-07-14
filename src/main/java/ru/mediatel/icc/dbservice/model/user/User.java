package ru.mediatel.icc.dbservice.model.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mediatel.icc.dbservice.common.UuidEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends UuidEntity {
    private String phone;
    private String email;
    private String tg;
    private String description;

    public User(UUID id, String phone, String email, String tg, String description) {
        super(id);
        this.phone = phone;
        this.email = email;
        this.tg = tg;
        this.description = description;
    }
}
