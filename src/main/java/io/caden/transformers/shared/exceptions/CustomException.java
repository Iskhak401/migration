package io.caden.transformers.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public final class CustomException extends ResponseStatusException {

    /**
     * .
     * constructor to set the EventException object
     *
     * @param message message of the exception
     * @param status http Status. Greater than 400.
     */
    private CustomException(final HttpStatus status,
                            final String message) {
        super(status, message);
    }

    /**
     * @param code    http code for which the exception has to be thrown.
     * @param message message of the exception
     * @return EventException.
     */
    public static CustomException withCode(final HttpStatus code, final String message) {
        return new CustomException(code, message);
    }

    /**
     * @param code http code for which the exception has to be thrown.
     * @return Event Exception class.
     */
    public static CustomException withCode(final HttpStatus code) {
        return withCode(code, null);
    }
}
