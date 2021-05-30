package com.hunza.caterer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact
{
    private String phoneNumber;
    private String mobileNumber;
    private String emailAddress;
}
