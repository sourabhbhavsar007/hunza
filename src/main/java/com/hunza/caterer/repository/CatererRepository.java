package com.hunza.caterer.repository;

import com.hunza.caterer.domain.Caterer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatererRepository extends MongoRepository<Caterer, String>
{
    List<Caterer> findByName(String name);

    Page<Caterer> findByAddress_City(String city, Pageable pageRequest);
}
