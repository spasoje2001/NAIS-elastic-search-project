package com.veljko121.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Item;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>{

    @Query("""
            SELECT i\s
            FROM Item i\s
            WHERE UPPER(i.name) LIKE UPPER(?1) OR UPPER(i.name) LIKE (UPPER('%' || ?1 || '%'))""")
    List<Item> findByName(String name);


}
