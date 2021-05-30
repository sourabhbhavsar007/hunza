package com.hunza.caterer.dto;

import com.hunza.caterer.domain.BaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

public class BaseDTO extends RepresentationModel
{
    public <T extends BaseEntity> void fillEntity(T entity)
    {
        postMapper(entity.getClass()).map(this, entity);
    }

    public <T extends BaseEntity, K extends BaseDTO> K fillDTO(T entity)
    {
        getMapper(entity.getClass()).map(entity, this);
        return (K) this;
    }

    protected ModelMapper getMapper(Class<? extends BaseEntity> clazz)
    {
        return new ModelMapper();
    }

    protected ModelMapper postMapper(Class<? extends BaseEntity> clazz)
    {
        return getMapper(clazz);
    }
}
