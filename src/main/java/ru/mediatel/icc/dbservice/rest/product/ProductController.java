package ru.mediatel.icc.dbservice.rest.product;


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
import ru.mediatel.icc.dbservice.model.product.Product;
import ru.mediatel.icc.dbservice.model.product.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.mediatel.icc.dbservice.common.Constants.BASIC_PATH_V1;


@RestController
@RequestMapping(BASIC_PATH_V1 + "/products")
public class ProductController {
    private final ProductService subscriptionService;


    public ProductController(ProductService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping()
    public PagedDataApiResponse<ProductDto> list(

            @RequestParam(name = "quantity_le", required = false) String quantityLe,
            @RequestParam(name = "quantity_ge", required = false) String quantityGe,
            @RequestParam(name = "price_le", required = false) String priceLe,
            @RequestParam(name = "price_ge", required = false) String priceGe,
            @RequestParam(name = "description", required = false) String description,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<Product> cmdItems = subscriptionService.search(new HashMap<>() {{

            put("quantity_le", quantityLe);
            put("quantity_ge", quantityGe);
            put("price_le", priceLe);
            put("price_ge", priceGe);
            put("description", description);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<ProductDto> dtoList = cmdItems.getItems().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<ProductDto> getById(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new ProductDto(subscriptionService.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid ProductDto dto
    ) {
        subscriptionService.create(
                new Product(
                        UUID.randomUUID(),
                        dto.getQuantity(),
                        dto.getPrice(),
                        dto.getDescription()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid ProductDto dto
    ) {
        subscriptionService.update(
                new Product(
                        id,
                        dto.getQuantity(),
                        dto.getPrice(),
                        dto.getDescription()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        subscriptionService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}
