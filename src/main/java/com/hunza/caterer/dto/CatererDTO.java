package com.hunza.caterer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class CatererDTO extends BaseDTO
{
    private String id;

    @NotBlank
    private String name;
    @Valid
    private AddressDTO address;
    @Valid
    private CapacityDTO capacity;
    @Valid
    private ContactDTO contact;
}
