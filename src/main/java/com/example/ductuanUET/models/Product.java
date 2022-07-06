package com.example.ductuanUET.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

//POJO
@Entity
@Table(name="tblProduct")
public class Product {
    //this is "primary key"
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 300)
    private String productName;
    private int productYear;
    private Double productPrice;
    private String productUrl;

    //default constructor
    public Product() {}

    //calculated field = trancient
    @Transient
    private  int age;

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - productYear;
    }

    public Product(String productName, int productYear, double productPrice, String productUrl) {
        this.productName = productName;
        this.productYear = productYear;
        this.productPrice = productPrice;
        this.productUrl = productUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productYear=" + productYear +
                ", productPrice=" + productPrice +
                ", productUrl='" + productUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productYear == product.productYear && age == product.age && Objects.equals(id, product.id) && Objects.equals(productName, product.productName) && Objects.equals(productPrice, product.productPrice) && Objects.equals(productUrl, product.productUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productYear, productPrice, productUrl, age);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}

