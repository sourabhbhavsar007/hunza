package com.hunza.caterer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address
{
    private String city;
    private String streetName;
    private Integer streetNumber;
    private String postalCode;
}
