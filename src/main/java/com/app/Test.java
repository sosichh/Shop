package com.app;

import com.app.domain.Customer;
import com.app.repository.CustomerRepository;

// Not to github
public class Test {
    public static void main(String[] args) throws Exception{
        // Product product = new Product(1, "Apple", 120, true);

        // System.out.println(product);

        // ObjectMapper mapper = new ObjectMapper();

        // mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // File file = new File("database\\product.txt");

        // try {
        //     mapper.writeValue(file, product);

        //     Product productFromFile = mapper.readValue(file, Product.class);

        //     System.out.println("Readed product:");
        //     System.out.println(productFromFile);

            // List<Product> products = new ArrayList<>(Arrays.asList(
            //     new Product(1, "Яблоко", 75, true),
            //     new Product(2, "Виноград", 175, true),
            //     new Product(3, "Апельсин", 105, true)
            // ));
            
        //     mapper.writeValue(file, products);
            

        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        // ProductRepository repository = new ProductRepository();

        // Product product = new Product(3, "Peach", 190, true);

        // repository.save(product);
        // repository.save(new Product(1, "Apple", 75, true));
        // repository.save(new Product(2, "Vine", 175, true));
        // repository.save(new Product(3, "Orange", 105, true));

        // repository.findAll().forEach(System.out::println);

        // Product productById = repository.findById(1);
        // System.out.println("\n" + productById);

        // Product newProduct = new Product(2, "Apple", 100, true);
        // repository.update(newProduct);

        // repository.deleteById(3);

        CustomerRepository repository = new CustomerRepository();

        Customer customer1 = new Customer();
        customer1.setName("Michael");
        customer1.setActive(true);

        Customer customer2 = new Customer();
        customer2.setName("Maria");
        customer2.setActive(true);

        Customer customer3 = new Customer();
        customer3.setName("Lion");
        customer3.setActive(true);

        repository.save(customer1);
        repository.save(customer2);
        repository.save(customer3);
    }
}
