package com.ag04.feeddit.services;


import com.ag04.feeddit.domain.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Iterable<Product> listAllProducts();

    Product getProductById(Integer id);

    Product saveProduct(Product product);

    void deleteProduct(Integer id);
}