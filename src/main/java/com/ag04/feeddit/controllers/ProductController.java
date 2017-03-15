package com.ag04.feeddit.controllers;

import com.ag04.feeddit.domain.Product;
import com.ag04.feeddit.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko on 15.03.17..
 */

@Controller
public class ProductController {

    @Autowired
    ProductService productService;


    @RequestMapping("product/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "productshow";
    }

    @RequestMapping("products")
    public String showAllProducts(Model model){
        List<Product> allProducts = new ArrayList<>();
        productService.listAllProducts().forEach(allProducts::add);
        model.addAttribute("listOfProducts", allProducts);
        return "products";
    }

    @RequestMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @RequestMapping(value = "product", method = RequestMethod.POST)
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/product/" + product.getId();
    }

}
