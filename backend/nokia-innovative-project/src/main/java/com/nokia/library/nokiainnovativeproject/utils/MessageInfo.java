package com.nokia.library.nokiainnovativeproject.utils;

import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MessageInfo {

	private Boolean success;
	private Object object;
	private List<String> message;

	public static ResponseEntity success(Object object, List<String> message){
		return ResponseEntity.ok().body(new MessageInfo(true, object, message));
	}

	public static ResponseEntity failure(List<String> message) {
		return ResponseEntity.badRequest().body(new MessageInfo(false, null, message));
	}

	public static void validateBindingResults(BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			List<String> errorsList = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

			throw new ValidationException(errorsList.stream().collect(Collectors.joining(". \n")));
		}
	}

	public static void isThisEntityUnique(Long count, String entityName) {
		if (count > 0) throw new ValidationException("This " + entityName +
					" already exist in database.");
	}
}
