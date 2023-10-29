package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;

public class Computer implements Saveable
{
    private String name;
    private String model;
    private ArrayList <Option> options = new ArrayList<>();

    public Computer(String name, String model)
    {
        this.name = name;
        this.model = model;
    }
    public Computer(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.model = br.readLine();

        int size = Integer.parseInt(br.readLine());
        while(size-- > 0) options.add(new Option(br));
    }
    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write(model + '\n');
        for(Option opt: options)
            opt.save(bw);
    }

    public void addOption(Option option)
    {
        options.add(option);
    }

    public long cost()
    {
        long sum = 0;
        for(int i = 0; i < options.size(); i++)
            sum += options.get(i).cost();
        return sum;
    }

    @Override
    public String toString()
    {
        StringBuilder printComputer = new StringBuilder();
        
        for(int i = 0; i < options.size(); i++)
            printComputer.append(options.get(i));
        
        printComputer.append(" ($" + cost() + ")\n");

        return printComputer.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        Computer c = (Computer)o;

        if(o == this)
            return true;
        if(!(o instanceof Computer))
            return false;
        
        return c.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + options.hashCode();
        return result;
    }
}
