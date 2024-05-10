package ru.anastasiya.exceptions;

public class CategoryServiceException extends RuntimeException {
    public CategoryServiceException(String message) {
        super(message);
    }

    public static CategoryServiceException CategoryExist(String name) {
        return new CategoryServiceException("Category with such name: " + name + " already exists");
    }

    public static CategoryServiceException CategoryNotExist(String name) {
        return new CategoryServiceException("Category with such name: " + name + " does not exist");
    }

    public static CategoryServiceException MccExist() {
        return new CategoryServiceException("mcc already reserved for another category");
    }
}
