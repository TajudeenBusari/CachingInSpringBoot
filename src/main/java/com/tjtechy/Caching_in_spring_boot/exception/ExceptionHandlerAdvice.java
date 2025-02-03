package com.tjtechy.Caching_in_spring_boot.exception;

import com.tjtechy.Caching_in_spring_boot.exception.modelNotFound.DepartmentNotFoundException;
import com.tjtechy.Caching_in_spring_boot.system.Result;
import com.tjtechy.Caching_in_spring_boot.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * This class is used to handle exceptions in the controller layer
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(DepartmentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  Result handleDepartmentNotFoundException(DepartmentNotFoundException e) {
    return new Result(e.getMessage(), false, StatusCode.NOT_FOUND);
  }

  //for any other unhanded exception
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  Result handleOtherException(Exception e) {
    return new Result("A server internal error occurs", false, e.getMessage(), StatusCode.INTERNAL_SERVER_ERROR );
  }

}
