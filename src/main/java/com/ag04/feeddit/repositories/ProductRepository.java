package com.ag04.feeddit.repositories;

import com.ag04.feeddit.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
