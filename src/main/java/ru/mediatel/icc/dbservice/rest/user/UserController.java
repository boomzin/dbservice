package ru.mediatel.icc.dbservice.rest.user;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mediatel.icc.dbservice.common.data.PagedResult;
import ru.mediatel.icc.dbservice.common.response.DataApiResponse;
import ru.mediatel.icc.dbservice.common.response.PagedDataApiResponse;
import ru.mediatel.icc.dbservice.common.response.StatusApiResponse;
import ru.mediatel.icc.dbservice.model.user.User;
import ru.mediatel.icc.dbservice.model.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.mediatel.icc.dbservice.common.Constants.BASIC_PATH_V1;


@RestController
@RequestMapping(BASIC_PATH_V1 + "/users")
public class UserController {
    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public PagedDataApiResponse<UserDto> list(

            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "tg", required = false) String tg,
            @RequestParam(name = "description", required = false) String description,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<User> cmdItems = service.search(new HashMap<>() {{

            put("phone", phone);
            put("email", email);
            put("tg", tg);
            put("description", description);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<UserDto> dtoList = cmdItems.getItems().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<UserDto> getById(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new UserDto(service.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid UserDto dto
    ) {
        service.create(
                new User(
                        UUID.randomUUID(),
                        dto.getEmail(),
                        dto.getEmail(),
                        dto.getTg(),
                        dto.getDescription()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid UserDto dto
    ) {
        service.update(
                new User(
                        id,
                        dto.getPhone(),
                        dto.getEmail(),
                        dto.getTg(),
                        dto.getDescription()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        service.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}
