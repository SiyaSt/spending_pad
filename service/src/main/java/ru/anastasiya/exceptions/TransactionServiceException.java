package ru.anastasiya.exceptions;

public class TransactionServiceException extends RuntimeException {
    public TransactionServiceException(String message) {
        super(message);
    }
    public static TransactionServiceException transactionNotExist(){
        return new TransactionServiceException("Transaction not exist");
    }

    public static TransactionServiceException categoryNotFound(String category){
        return new TransactionServiceException("Category not found: " + category);
    }
}
