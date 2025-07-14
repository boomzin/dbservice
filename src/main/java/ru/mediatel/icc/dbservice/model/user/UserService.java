package ru.mediatel.icc.dbservice.model.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mediatel.icc.dbservice.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(UUID userId) {
        return repository.findById(userId);
    }

    public PagedResult<User> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void create(User user) {
        repository.create(user);
    }

    public void delete(UUID userId) {
        repository.delete(userId);
    }

}
