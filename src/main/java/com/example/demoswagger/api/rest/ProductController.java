package com.example.demoswagger.api.rest;

import com.example.demoswagger.api.model.Product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Product API", description = "Api  managing the products. ")
public class ProductController {

    List<Product> products = new ArrayList<>();

    public ProductController() {
        Product p1 = Product.builder().id(1).name("product1")
                .price(new BigDecimal("12.2")).build();
        Product p2 = Product.builder().id(2).name("product2")
                .price(new BigDecimal("10.2")).build();
        Product p3 = Product.builder().id(3).name("product3")
                .price(new BigDecimal("98.2")).build();
        Product p4 = Product.builder().id(4).name("product4")
                .price(new BigDecimal("18.2")).build();

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
    }

    @Operation(summary = "Get all products", tags = {"get all"})
    @ApiResponses({@ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @RequestMapping(value = "/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProducts() {
        return products;
    }


    @Operation(summary = "Get a product by its id", tags = {"get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "204", description = "There are no Products", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@Parameter(description = "id of product to be searched", required = true) @PathVariable int id) {
        return products.get(id);
    }


    @Operation(summary = "Save new Product", tags = "save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully persisted", content = {
                    @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request",
                    content = {@Content(schema = @Schema())})
    }
    )
    @RequestMapping(value = "/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveProduct(@RequestBody Product p) {
        p.setId(products.size());

        products.add(p);
        System.out.println(products);
        return new ResponseEntity<Product>(p, HttpStatus.CREATED);

    }


    @Operation(summary = "Update  Product", tags = "update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "205", description = "Successfully updated", content = {
                    @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to reach the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),

            @ApiResponse(responseCode = "500", description = "Application failed to process the request",
                    content = {@Content(schema = @Schema())})
    }
    )
    @RequestMapping(value = "/product", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product p) {
        if (p.getId() - 1 < 0) {
            return new ResponseEntity<Product>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Product product = products.get((int) (p.getId() - 1));
        products.remove(product);
        products.add((int) (p.getId() - 1), p);

        return new ResponseEntity<Product>(p, HttpStatus.ACCEPTED);

    }

    @Operation(summary = "Delete a Product by Id", tags = {"products", "delete"})
    @ApiResponses({@ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
        try {
            products.remove(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "Delete all Products", tags = {"products", "delete"})
    @ApiResponses({@ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/product")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            products.clear();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
