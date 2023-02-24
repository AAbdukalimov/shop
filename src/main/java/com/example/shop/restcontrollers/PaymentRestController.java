package com.example.shop.restcontrollers;

import com.example.shop.dto.PaymentDto;
import com.example.shop.entities.Payment;
import com.example.shop.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Payments REST controller", description = "Payments API")
@RequestMapping("/rest/payments")
@RestController
@AllArgsConstructor
public class PaymentRestController {

    private PaymentService paymentService;

    @PostMapping()
    @Operation(summary = "Create payment")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@Valid @RequestBody PaymentDto paymentDto) {
        return paymentService.create(paymentService.toPayment(paymentDto));
    }

    @GetMapping("{id}")
    @Operation(summary = "Find payment by id")
    @ResponseStatus(HttpStatus.OK)
    public Payment findById(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @GetMapping()
    @Operation(summary = "Find all payments")
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> findAll() {
        return paymentService.findAll();
    }

    @GetMapping("/findAllPaymentsByUserId/{id}")
    @Operation(summary = "Find all payments by user id")
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> findAllByUserId(@PathVariable Long id) {
        return paymentService.findAllByUserId(id);
    }

    @PostMapping("/dateFormatter")
    @Operation(summary = "Formatting local date and time")
    @ResponseStatus(HttpStatus.OK)
    public String dateFormatter(@RequestBody LocalDateTime localDateTime) {
        return paymentService.dateFormatter(localDateTime);
    }

}
