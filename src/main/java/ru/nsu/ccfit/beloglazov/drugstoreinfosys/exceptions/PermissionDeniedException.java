package ru.nsu.ccfit.beloglazov.drugstoreinfosys.exceptions;

public class PermissionDeniedException extends Exception {
    public PermissionDeniedException(String message) {
        super(message);
    }
}