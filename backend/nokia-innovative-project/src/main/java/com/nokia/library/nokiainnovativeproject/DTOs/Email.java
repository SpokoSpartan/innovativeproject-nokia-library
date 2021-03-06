package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @NotBlank(message = "The subject of the message is required")
    @Size(max = 200, message = "Subject can't exceed 200 characters")
    private String subject;

    @NotBlank(message = "The message can't be empty")
    @Size(max = 5000, message = "Message can't exceed 5000 characters")
    private String messageContext;
}