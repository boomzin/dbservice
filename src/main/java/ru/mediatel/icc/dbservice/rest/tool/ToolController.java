package ru.mediatel.icc.dbservice.rest.tool;


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
import ru.mediatel.icc.dbservice.model.tool.Tool;
import ru.mediatel.icc.dbservice.model.tool.ToolService;
import ru.mediatel.icc.dbservice.model.user.User;
import ru.mediatel.icc.dbservice.model.user.UserService;
import ru.mediatel.icc.dbservice.rest.user.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.mediatel.icc.dbservice.common.Constants.BASIC_PATH_V1;


@RestController
@RequestMapping(BASIC_PATH_V1 + "/tools")
public class ToolController {
    private final ToolService service;


    public ToolController(ToolService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Tool> list() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<Tool> getById(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(service.findById(id));
    }

    @PostMapping()
    public DataApiResponse<Tool> create(
            @RequestBody Tool tool
    ) {
        return new DataApiResponse<>(service.saveOrUpdate(tool));
    }

}
