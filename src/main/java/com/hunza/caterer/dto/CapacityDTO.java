package com.hunza.caterer.dto;

import com.hunza.caterer.dto.validator.Capacity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Capacity
public class CapacityDTO
{
    @NonNull
    @Min(1)
    private Integer min;
    @NonNull
    @Min(2)
    private Integer max;
}
