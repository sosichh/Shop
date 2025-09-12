package com.app.sevice;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.app.Exceptions.CustomerNotFoundException;
import com.app.Exceptions.CustomerSaveException;
import com.app.Exceptions.CustomerUpdateException;
import com.app.Exceptions.ProductNotFoundException;
import com.app.domain.Customer;
import com.app.domain.Product;
import com.app.repository.CustomerRepository;


public class CustomerService {

    private final CustomerRepository repository;
    private final ProductService ProductService;

    public CustomerService() throws IOException{
        repository = new CustomerRepository();
        ProductService = new ProductService();
    }

    public void save(Customer customer) throws IOException, CustomerSaveException{
        if (customer == null){
            throw new CustomerSaveException("customer cannot be null");
        }

        String name = customer.getName();

        if (name == null || name.trim().isEmpty()){
            throw new CustomerSaveException("Name cannot be null or empty");
        }

        customer.setActive(true);
        repository.save(customer);
    }

    public List<Customer> GetAllActiveCustomers() throws IOException{
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .collect(Collectors.toList());
    }

    public Customer getActiveCustomerById(int id) throws IOException, CustomerNotFoundException{
        Customer customer = repository.findById(id);

        if (customer == null || !customer.isActive()){
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }

    public void update(Customer customer) throws IOException, CustomerUpdateException{
        if (customer == null || !customer.isActive()){
            throw new CustomerUpdateException("Customer cannot be null");
        }

        String name = customer.getName();

        if (name == null || name.trim().isEmpty()){
            throw new CustomerUpdateException("Customer's cannot be empty");
        }

        repository.update(customer);
    }

    public void deleteById(int id) throws IOException, CustomerNotFoundException{
        getActiveCustomerById(id).setActive(false);
    }

    public void deleteByName(String name) throws IOException{
        GetAllActiveCustomers()
            .stream()
            .filter(x -> x.getName().equals(name))
            .forEach(x -> x.setActive(false));
    }

    public void restoreById(int id) throws IOException, CustomerNotFoundException{
        Customer customer = repository.findById(id);

        if (customer != null){
            customer.setActive(true);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    public int getActiveCustomerCount() throws IOException{
        return GetAllActiveCustomers().size();
    }

    public double getCustomerCartTotalPrice(int id) throws IOException, CustomerNotFoundException{
        return getActiveCustomerById(id)
                .getProducts()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public double getCustomerAveragePrice(int id) throws IOException, CustomerNotFoundException{
        return getActiveCustomerById(id)
                .getProducts()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0);
    }

    public void addProductToCustomerCart(int customerId, int productId) throws IOException, CustomerNotFoundException, ProductNotFoundException{
        Customer customer = getActiveCustomerById(customerId);
        Product product = ProductService.getActiveProductById(productId);

        customer.getProducts().add(product);
    }

    public void deleteProductFromCustomerCart(int customerId, int productId) throws IOException, CustomerNotFoundException, ProductNotFoundException {
        Customer customer = getActiveCustomerById(customerId);
        Product product = ProductService.getActiveProductById(productId);

        customer.getProducts().remove(product);
    }

    public void clearCustomerCart(int id) throws IOException, CustomerNotFoundException{
        getActiveCustomerById(id).getProducts().clear();
    }
}
