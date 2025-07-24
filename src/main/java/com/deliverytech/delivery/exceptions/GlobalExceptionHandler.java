package com.deliverytech.delivery.exceptions;

import com.deliverytech.delivery.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Erro de validação nos dados enviados",
                request.getDescription(false).replace("uri=", "")
        );
        errorResponseDTO.setErrorCode("VALIDATION_ERROR");
        errorResponseDTO.setDetails(errors);
        return new ResponseEntity<>(errorResponseDTO,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "ENTITY_NOT_FOUND",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        errorResponseDTO.setErrorCode(ex.getErrorCode());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(
            ConflictException ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        if (ex.getConflictField() != null) {
            details.put(ex.getConflictField(), ex.getConflictValue().toString());
        }
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                "Conflito de dados",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        errorResponseDTO.setErrorCode(ex.getErrorCode());
        errorResponseDTO.setDetails(details.isEmpty() ? null : details);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getDescription(false).replace("uri=", "")
        );
        errorResponseDTO.setErrorCode("INTERNAL_ERROR");
        return new ResponseEntity<>(errorResponseDTO,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<ErrorResponseDTO> handleBusinessException(
//            BusinessException ex, WebRequest request) {
//        Map<String, String> details = new HashMap<>();
//
//        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
//                HttpStatus.NOT_FOUND.value(),
//                "Erro de negócio",
//                ex.getMessage(),
//                request.getDescription(false).replace("uri=", "")
//        );
//        errorResponse.setErrorCode(ex.getErrorCode());
//        return new ResponseEntity<>(errorResponse,
//                HttpStatus.NOT_FOUND);
//    }
}
