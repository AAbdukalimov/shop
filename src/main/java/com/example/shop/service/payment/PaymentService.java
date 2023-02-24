package com.example.shop.service.payment;


import com.example.shop.dto.PaymentDto;
import com.example.shop.entities.Payment;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {

    Payment create(Payment payment);
    Payment findById(Long id);
    List<Payment>findAll();
    List<Payment> findAllByUserId(Long id);
    void deleteById(Long id);
    Page<Payment> findPage(int currentPage);
    String dateFormatter(LocalDateTime dateTime);
    Payment toPayment(PaymentDto paymentDto);

}
