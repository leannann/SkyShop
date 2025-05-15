package org.skypro.skyshop.exception;

import org.skypro.skyshop.model.error.ShopError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProduct(NoSuchProductException ex) {
        ShopError error = new ShopError(
                "product_not_found",             // код ошибки (произвольный, но машиночитаемый)
                "Продукт не найден: " + ex.getMessage()  // читаемое сообщение
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}