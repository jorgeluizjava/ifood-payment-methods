package br.com.challenge.ifoodpaymentmethods.paymentmethods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ErroAplicacao handleErroAplicacao(Exception exception) {
        return new ErroAplicacao(exception.getMessage());
    }
}
