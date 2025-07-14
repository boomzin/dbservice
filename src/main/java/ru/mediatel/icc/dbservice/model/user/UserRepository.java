package ru.mediatel.icc.dbservice.model.user;


import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface UserRepository {
    void create(User user);

    void update(User user);

    User findById(UUID userId);

    PagedResult<User> search(Map<String, String> apiParams);

    void delete(UUID userId);
}
