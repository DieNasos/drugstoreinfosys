package ru.nsu.ccfit.beloglazov.drugstoreinfosys.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}