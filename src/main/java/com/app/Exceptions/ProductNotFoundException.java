package com.app.Exceptions;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(int id) {
        super(String.format("Product with identifier %d not found", id));
    }
    
}