package com.example.shop.service;

import com.example.shop.entities.Payment;
import com.example.shop.entities.User;
import com.example.shop.repository.payment.PaymentRepository;
import com.example.shop.service.payment.PaymentServiceImpl;
import com.example.shop.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private HttpSession session;
    @Mock
    private UserService userService;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment rawPayment;
    private Payment firstPayment;
    private Payment secondPayment;
    private Payment thirdPayment;
    private User firstUser;
    private User secondUser;

    @BeforeEach
    public void init() {
        rawPayment = Payment.builder()
                .id(null)
                .amount(null)
                .date(null)
                .user(null)
                .itemList(null)
                .build();

        firstPayment = Payment.builder()
                .id(1L)
                .amount(30.0)
                .date(LocalDateTime.now())
                .user(firstUser)
                .itemList(null)
                .build();

        secondPayment = Payment.builder()
                .id(2L)
                .amount(20.0)
                .date(LocalDateTime.now())
                .user(firstUser)
                .itemList(null)
                .build();

        thirdPayment = Payment.builder()
                .id(3L)
                .amount(40.0)
                .date(LocalDateTime.now())
                .user(secondUser)
                .itemList(null)
                .build();

        firstUser = User.builder()
                .id(1L)
                .firstName("first")
                .lastName("first")
                .userName("first_1")
                .build();

        secondUser = User.builder()
                .id(2L)
                .firstName("second")
                .lastName("second")
                .userName("second_2")
                .build();
    }

    @Test
    public void testCreate() {
        Double total = 30.0;
        session.setAttribute("total", total);

        when(session.getAttribute("total")).thenReturn(total);
        when(userService.findByUserName(userService.getCurrentUsername())).thenReturn(firstUser);
        when(paymentRepository.save(rawPayment)).thenReturn(firstPayment);

        Payment actual = paymentService.create(rawPayment);
        assertEquals(firstPayment, actual);
    }

    @Test
    public void testFindById() {
        when(paymentRepository.findById(firstPayment.getId())).thenReturn(Optional.ofNullable(firstPayment));
        Payment actual = paymentService.findById(firstPayment.getId());
        assertEquals(firstPayment, actual);
    }

    @Test
    public void testFindAll() {
        List<Payment> expected = List.of(firstPayment, secondPayment, thirdPayment);
        when(paymentRepository.findAll()).thenReturn(expected);
        List<Payment> actual = paymentService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllByUserId() {
        List<Payment> expected = List.of(firstPayment, secondPayment);
        when(paymentRepository.findAllByUserId(firstUser.getId())).thenReturn(expected);
        List<Payment> actual = paymentService.findAllByUserId(firstUser.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testDateFormatter() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 2, 19, 11, 23, 8, 587249900);
        String expected = "Sun, 19-02-2023 11:23:08";
        String actual = paymentService.dateFormatter(dateTime);
        assertEquals(expected, actual);
    }

}
