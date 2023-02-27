package com.example.shop.service.payment;


import com.example.shop.dto.PaymentDto;
import com.example.shop.entities.Payment;
import com.example.shop.repository.payment.PaymentRepository;
import com.example.shop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import static com.example.shop.util.Constants.*;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private UserService userService;


    @Override
    @Transactional
    public Payment create(Payment payment) {
        payment.setUser(userService.findByUserName(userService.getCurrentUsername()));
        payment.setDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Page<Payment> findPage(int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, 5);
        return paymentRepository.findAll(pageable);
    }

    @Override
    public List<Payment> findAllByUserId(Long id) {
                return paymentRepository.findAllByUserId(id);
    }

    @Override
    public String dateFormatter(LocalDateTime dateTime) {
        DateTimeFormatter aFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return dateTime.format(aFormatter.withLocale(Locale.US));
    }

    @Override
    public Payment toPayment(PaymentDto paymentDto) {
        return Payment.builder()
                .amount(paymentDto.getAmount())
                .date(paymentDto.getDate())
                .build();
    }

}
