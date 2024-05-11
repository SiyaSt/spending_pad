package ru.anastasiya.exceptions;

public class TransactionServiceException extends RuntimeException {
    public TransactionServiceException(String message) {
        super(message);
    }
    public static TransactionServiceException mccNotFound(Integer mcc){
        return new TransactionServiceException("Mcc not found: " + mcc);
    }

    public static TransactionServiceException categoryNotFound(String category){
        return new TransactionServiceException("Category not found: " + category);
    }
}
