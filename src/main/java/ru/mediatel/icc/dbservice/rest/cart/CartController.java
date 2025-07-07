package ru.mediatel.icc.dbservice.rest.cart;


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
import ru.mediatel.icc.dbservice.model.cart.Cart;
import ru.mediatel.icc.dbservice.model.cart.CartService;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.mediatel.icc.dbservice.common.Constants.BASIC_PATH_V1;


@RestController
@RequestMapping(BASIC_PATH_V1 + "/carts")
public class CartController {
    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public PagedDataApiResponse<CartDto> list(

            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "created_at", required = false) String createdAt,
            @RequestParam(name = "created_at_from", required = false) String createdAtFrom,
            @RequestParam(name = "created_at_to", required = false) String createdAtTo,
            @RequestParam(name = "updated_at", required = false) String updatedAt,
            @RequestParam(name = "updated_at_from", required = false) String updatedAtFrom,
            @RequestParam(name = "updated_at_to", required = false) String updatedAtTo,
            @RequestParam(name = "customer_comment", required = false) String customerComment,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<Cart> cmdItems = cartService.search(new HashMap<>() {{

            put("status", status);
            put("created_at", createdAt);
            put("created_at_from", createdAtFrom);
            put("created_at_to", createdAtTo);
            put("updated_at", updatedAt);
            put("updated_at_frpm", updatedAtFrom);
            put("updated_at_to", updatedAtTo);
            put("customer_comment", customerComment);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<CartDto> dtoList = cmdItems.getItems().stream()
                .map(CartDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<CartDto> getById(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new CartDto(cartService.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid CartDto dto
    ) {
        cartService.create(
                new Cart(
                        UUID.randomUUID(),
                        dto.getUserId(),
                        dto.getStatus(),
                        dto.getCreated(),
                        dto.getUpdated(),
                        dto.getCustomerComment()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid CartDto dto
    ) {
        cartService.update(
                new Cart(
                        id,
                        dto.getUserId(),
                        dto.getStatus(),
                        dto.getCreated(),
                        dto.getUpdated(),
                        dto.getCustomerComment()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        cartService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}
