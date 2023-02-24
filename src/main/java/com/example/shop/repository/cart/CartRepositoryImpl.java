package com.example.shop.repository.cart;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void resetCart() {
        jdbcTemplate.execute("TRUNCATE shop.cart;");
    }
}
