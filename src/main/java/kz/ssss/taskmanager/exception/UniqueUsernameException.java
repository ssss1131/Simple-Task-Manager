package kz.ssss.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UniqueUsernameException extends RuntimeException {

    public UniqueUsernameException(String message) {
        super(message);
    }
}
