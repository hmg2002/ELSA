package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;

public class Store {
    public Store(String name) {
        this.name = name;
    }
    public Store(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        int size1 = Integer.parseInt(br.readLine());
        while(size1-- > 0) customers.add(new Customer(br));

        int size2 = Integer.parseInt(br.readLine());
        while(size2-- > 0) options.add(new Option(br));

        int size3 = Integer.parseInt(br.readLine());
        while(size3-- > 0) computers.add(new Computer(br));
        
        int size4 = Integer.parseInt(br.readLine());
        while(size4-- > 0) orders.add(new Order(br));
    }
    public void save(BufferedWriter bw) throws IOException
    {
        saveSet(bw, customers);
        saveSet(bw, options);
        saveSet(bw, computers);
        saveSet(bw, orders);
    }
    private <T extends Saveable> void saveSet(BufferedWriter bw, Set<T> set) throws IOException
    {
        bw.write(set.size() + "\n");
        for (T item : set) {
            item.save(bw);
        }
    }
    public String name() {
        return this.name;
    }
    
    // ////////////////////////////////////////////////////////////
    // Customers
    
    public void add(Customer customer) {
        if(!customers.contains(customer)) customers.add(customer);
    }
    public Object[] customers() {
        return this.customers.toArray();
    }
    
    // ///////////////////////////////////////////////////////////
    // Options
    
    public void add(Option option) {
        if(!options.contains(option)) options.add(option);
    }
    public Object[] options() {
        return this.options.toArray();
    }
    
    // ///////////////////////////////////////////////////////////
    // Computers
    
    public void add(Computer computer) {
        if(!computers.contains(computer)) computers.add(computer);
    }
    public Object[] computers() {
        return this.computers.toArray();
    }
    
    // ///////////////////////////////////////////////////////////
    // Orders
    
    public void add(Order order) {
        if(!orders.contains(order)) orders.add(order);
    }
    public Object[] orders() {
        return this.orders.toArray();
    }

    // ///////////////////////////////////////////////////////////
    // Fields
    
    private String name;
    private TreeSet<Customer> customers = new TreeSet<>();
    private HashSet<Option> options = new HashSet<>();
    private HashSet<Computer> computers = new HashSet<>();
    private HashSet<Order> orders = new HashSet<>();
}
