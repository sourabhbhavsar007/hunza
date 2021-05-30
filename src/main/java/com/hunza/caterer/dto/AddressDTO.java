package com.hunza.caterer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO
{
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]*$",
            message = "Must not contain digits or special characters")
    private String city;
    private String streetName;
    private Integer streetNumber;
    private String postalCode;
}