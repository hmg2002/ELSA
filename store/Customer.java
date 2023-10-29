package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;

public class Customer implements Comparable<Customer>, Saveable
{
    private String name;
    private String email;

    public Customer(String name, String email)
    {
        this.name = name;
        this.email = email;

        int atIndex = email.indexOf('@', 0);
        int dotIndex = (atIndex >= 0) ? email.indexOf('.', atIndex) : -1;
        if(dotIndex < 0) // will be true if atIndex < 0, so don't check that
            throw new IllegalArgumentException("Invalid email address: " + email);
    }
    public Customer(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.email = br.readLine();
    }
    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write(email + '\n');
    }

    @Override
    public String toString()
    {
        return name + " (" + email + ")\n";
    }

    @Override
    public boolean equals(Object o)
    {
        Customer c = (Customer)o;
        
        if(o == this)
            return true;
        if(!(o instanceof Customer))
            return false;
        
        return c.name.equals(this.name) && c.email.equals(this.email);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public int compareTo(Customer c) {
        int result = name.compareTo(c.name);
        if (result == 0) {
            result = email.compareTo(c.email);
        }
        return result;
    }
}
