package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;

public class Option implements Saveable
{
    protected String name;
    protected long cost;

    public Option(String name, long cost)
    {
        this.name = name;
        this.cost = cost;

        if(cost < 0)
        {
            throw new IllegalArgumentException("Cost is invalid.");
        }
    }
    public Option(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.cost = Long.parseLong(br.readLine());
    }
    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write("" + cost + '\n');
    }

    public long cost()
    {
        return ((long)(cost/100));
    }

    @Override
    public String toString()
    {
        return name + " ($" + cost() + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        Option c = (Option)o;
        
        if(o == this)
            return true;
        if(!(o instanceof Option))
            return false;
        
        return c.name.equals(this.name) && c.cost == cost;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (cost ^ (cost >>> 32));
        return result;
    }
}
