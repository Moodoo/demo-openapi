package com.example.demoswagger.api.model.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Schema(description = "Tutorial Model Information")
public class Product {
    @Schema(description = "Product id",accessMode = Schema.AccessMode.READ_ONLY)
    private long id=-1;
    @Schema(description = "Product name", example = "productABC")
    private String name;
    @Schema(description = "Product price", example = "100.2")
    private BigDecimal price;

}
