package com.example.ductuanUET.controllers;

import com.example.ductuanUET.models.Product;
import com.example.ductuanUET.models.ResponseObject;
import com.example.ductuanUET.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    //DI = Dependency Injection
    @Autowired
    private ProductRepository repository;



    //This request is : http://localhost:8080/api/v1/Products
    @GetMapping("")
    List<Product> getAllProducts() {
        return repository.findAll();
    }
    //Get detail product
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = repository.findById(id);
        if(foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query product successfully", foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with id = " + id, "")
            );
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
        if(foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Product name has already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Insert Product successfully", repository.save(newProduct))
        );
    }
    //update, upsert = update if found, otherwise insert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        Product updatedProduct = repository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setProductYear(newProduct.getProductYear());
                    product.setProductPrice(newProduct.getProductPrice());
                    return repository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Product successfully", updatedProduct)
        );
    }

    //Delete a Product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deletedProduct(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete product successfully","")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find product to delete","")
        );
    }

}
