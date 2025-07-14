package ru.mediatel.icc.dbservice.db.repository;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.exception.ObjectNotFoundException;
import ru.mediatel.icc.dbservice.common.search.SearchCriteria;
import ru.mediatel.icc.dbservice.common.search.SearchCriteriaSettings;
import ru.mediatel.icc.dbservice.db.generated.tables.records.UsersRecord;
import ru.mediatel.icc.dbservice.model.user.User;
import ru.mediatel.icc.dbservice.model.user.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mediatel.icc.dbservice.common.search.JooqSearchUtils.STR_LIKE_IC;
import static ru.mediatel.icc.dbservice.db.generated.Tables.USERS;


@Repository
public class JooqUserRepository implements UserRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<UsersRecord, User> mapper = r -> new User(
            r.getId(),
            r.getPhone(),
            r.getEmail(),
            r.getTg(),
            r.getDescription()
    );


    public JooqUserRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("phone", USERS.PHONE, STR_LIKE_IC)
                .filter("email", USERS.EMAIL, STR_LIKE_IC)
                .filter("tg", USERS.TG, STR_LIKE_IC)
                .filter("description", USERS.DESCRIPTION, STR_LIKE_IC)

                .build();
    }


    @Override
    public void create(User user) {
        UsersRecord record = db.newRecord(USERS);
        fillRecord(record, user);
        record.insert();
    }

    @Override
    public void update(User user) {
        UsersRecord record = db.fetchOptional(
                USERS,
                USERS.ID.eq(user.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(user.getId(), "User"));
        fillRecord(record, user);
        record.store();

    }

    @Override
    public User findById(UUID userId) {
        return db
                .selectFrom(USERS)
                .where(USERS.ID.eq(userId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(userId, "User"));
    }


    @Override
    public PagedResult<User> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(USERS);
        criteria.apply(step);

        List<User> list = step.fetch().map(record -> mapper.map((UsersRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(USERS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID userId) {
        db.deleteFrom(USERS)
                .where(USERS.ID.eq(userId))
                .execute();
    }

    private void fillRecord(UsersRecord record, User user) {
        record.setId(user.getId());
        record.setPhone(user.getPhone());
        record.setEmail(user.getEmail());
        record.setTg(user.getTg());
        record.setDescription(user.getDescription());
    }
}
