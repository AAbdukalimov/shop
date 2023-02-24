package com.example.shop.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ItemDto {

    @NotNull
    private Integer quantity;
    @NotNull
    private Double amount;

}
