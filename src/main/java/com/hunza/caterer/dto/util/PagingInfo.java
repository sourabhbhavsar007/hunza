package com.hunza.caterer.dto.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

import static java.lang.Math.ceil;

@Data
@NoArgsConstructor
public class PagingInfo implements Serializable
{
    private static final long serialVersionUID = 9092667832782814259L;

    private int pageIndex;
    private int pageSize;
    private long recordCount;
    private long pageCount;

    public PagingInfo(int pageIndex, int pageSize, long recordCount)
    {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.recordCount = recordCount;

        build();
    }

    public PagingInfo(PageRequest pageRequest, long recordCount)
    {
        pageIndex = pageRequest.getPageNumber();
        pageSize = pageRequest.getPageSize();
        this.recordCount = recordCount;

        build();
    }

    private void build()
    {
        if (pageSize > 0)
            pageCount = (int) ceil((double) recordCount / pageSize);
        else
            pageCount = 0;
    }
}

