package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;

public class Order implements Saveable
{
    private static long nextOrderNumber = 0;
    private long orderNumber;

    private Customer customer;
    private ArrayList<Computer> computers = new ArrayList<>();
    
    public Order(Customer customer)
    {
        this.customer = customer;
        orderNumber = nextOrderNumber++;
    }
    public Order(BufferedReader br) throws IOException
    {
        this.orderNumber = Long.parseLong(br.readLine());
        this.nextOrderNumber = Long.parseLong(br.readLine());

        Customer customer = new Customer(br);

        int size = Integer.parseInt(br.readLine());
        while(size-- > 0) computers.add(new Computer(br));
    }
    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write("" + nextOrderNumber + '\n');
        bw.write("" + orderNumber + '\n');
        customer.save(bw);
        for(Computer comp: computers)
            comp.save(bw);
    }

    public void addComputer(Computer computer)
    {
        computers.add(computer);
    }

    public long cost()
    {
        long totalCost = 0;
        for (Computer computer : this.computers)
            totalCost += computer.cost();
        
        return totalCost;
    }

    @Override
    public String toString()
    {
        StringBuilder printOrder = new StringBuilder();

        printOrder.append("Order " + orderNumber + " for " + customer.toString() + "\n");

        for(int i = 0; i < computers.size(); i++)
            printOrder.append(computers.get(i));

        printOrder.append(cost());
        
        return printOrder.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        Order c = (Order)o;
        
        if(o == this)
            return true;
        if(!(o instanceof Order))
            return false;
        
        return c.customer.equals(this.customer) && c.computers.equals(this.computers);
    }

    @Override
    public int hashCode() {
        int result = (int) (orderNumber ^ (orderNumber >>> 32));
        result = 31 * result + customer.hashCode();
        result = 31 * result + computers.hashCode();
        return result;
    }
}
