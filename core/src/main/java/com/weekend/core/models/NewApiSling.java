package com.weekend.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class NewApiSling {

    private List<Product> products;

    @PostConstruct
    protected void init() {

        products = new ArrayList<>();

        products.add(new Product("Laptop", "50000"));
        products.add(new Product("Mobile", "25000"));
        products.add(new Product("Headphones", "3000"));
        products.add(new Product("Smart Watch", "12000"));

    }

    public List<Product> getProducts() {
        return products;
    }

    public static class Product {

        private String name;
        private String price;

        public Product(String name, String price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

    }

}