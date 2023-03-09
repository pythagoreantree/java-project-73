package hexlet.code.dtos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ResponceError {

    private final String status;
    private final String message;

}
