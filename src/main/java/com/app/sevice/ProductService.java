package com.app.sevice;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.app.Exceptions.ProductNotFoundException;
import com.app.Exceptions.ProductSaveException;
import com.app.Exceptions.ProductUpdateException;
import com.app.domain.Product;
import com.app.repository.ProductRepository;

public class ProductService {
    private final ProductRepository repository;

    public ProductService() throws IOException{
        repository = new ProductRepository();

    }

    public void save(Product product) throws IOException, ProductSaveException{
        if (product == null){
            throw new ProductSaveException("Product cannot be null");
        }

        String title = product.getTitle();

        if (title == null || title.trim().isEmpty()){
            throw new ProductSaveException("Title cannot be null or empty");
        }

        if (product.getPrice() <= 0){
            throw new ProductSaveException("Price cannot be not more than 0");
        }


        product.setActive(true);
        repository.save(product);
    }

    public List<Product> GetAllActiveProducts() throws IOException{
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .collect(Collectors.toList());
    }

    public Product getActiveProductById(int id) throws IOException, ProductNotFoundException{
        Product product = repository.findById(id);

        if (product == null || !product.isActive()){
            throw new ProductNotFoundException(id);
        }

        return product;
    }

    public void update(Product product) throws IOException, ProductUpdateException{
        if (product == null){
            throw new ProductUpdateException("Product cannot be null");
        }

        if (product.getPrice() <= 0){
            throw new ProductUpdateException("Price must be more than 0");
        }
        
        repository.update(product);
    }

    public void deleteById(int id) throws IOException, ProductNotFoundException{
        getActiveProductById(id).setActive(false);
    }

    public void deleteByTitle(String title) throws IOException{
        GetAllActiveProducts()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));
    }

    public void restoreById(int id) throws IOException, ProductNotFoundException{
        Product product = repository.findById(id);
        
        if (product != null){
            product.setActive(true);
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    public int getActiveProductsCount() throws IOException{
        return GetAllActiveProducts().size();
    }

    public double getActiveProductsTotalCost() throws IOException{
        return GetAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public double getActiveProductsAveragePrice() throws IOException{
        int productCount = getActiveProductsCount();

        if (productCount == 0){
            return 0;
        }

        return getActiveProductsTotalCost() / productCount;
    }
}
