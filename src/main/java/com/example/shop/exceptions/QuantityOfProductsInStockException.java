package com.example.shop.exceptions;

public class QuantityOfProductsInStockException extends RuntimeException{

    public QuantityOfProductsInStockException(String message){
        super(message);
    }
}
