package com.example.shop.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto {

    @NotNull
    private Double amount;
    private LocalDateTime date;

}
