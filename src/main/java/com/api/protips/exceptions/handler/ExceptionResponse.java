package com.api.protips.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private LocalDateTime occurredIn = LocalDateTime.now();
    private String causedIn;

    public ExceptionResponse(HttpStatus status, String message, String causedIn, String... errors) {
        this.status = status;
        this.message = message;
        if (errors != null && errors.length > 0)
            this.errors = Arrays.asList(errors);
        this.causedIn = causedIn;

    }
}
