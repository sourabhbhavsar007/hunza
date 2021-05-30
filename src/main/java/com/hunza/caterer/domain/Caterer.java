package com.hunza.caterer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "caterer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Caterer extends BaseEntity
{
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private Address address;
    private Capacity capacity;
    private Contact contact;
}
