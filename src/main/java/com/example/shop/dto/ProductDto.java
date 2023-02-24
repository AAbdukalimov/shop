package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull(message = "Name is required")
    @Length(min = 2, message = "Product name should be from 2 characters")
    private String name;
    @Digits(integer = 7, fraction = 2, message = "only digits")
    private Double price;
    @DecimalMax("10000.0")
    private Integer quantity;
}
