package com.hunza.caterer.dto;

import com.hunza.caterer.dto.util.PagingInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CatererDTOList
{
    private PagingInfo pagingInfo;
    private List<CatererDTO> catererDTOS = new ArrayList<>();
}
