package com.hunza.caterer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO
{
    private String phoneNumber;
    @NonNull
    private String mobileNumber;
    @NonNull
    @Email( message = "Provide a valid email address")
    private String emailAddress;
}
