package com.ufcg.psoft.mercadofacil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}