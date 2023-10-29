package gui;

import store.Store;
import store.Customer;
import store.Option;
import store.Computer;
import store.Order;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import javax.swing.JFrame;           // for main window
import javax.swing.JOptionPane;      // for standard dialogs
import javax.swing.JPanel;
import javax.swing.JTextField;
// import javax.swing.JDialog;          // for custom dialogs (for alternate About dialog)
import javax.swing.JMenuBar;         // row of menu selections
import javax.swing.JMenu;            // menu selection that offers another menu
import javax.swing.JMenuItem;        // menu selection that does something
import javax.swing.JToolBar;         // row of buttons under the menu
import javax.swing.JButton;          // regular button
import javax.swing.JToggleButton;    // 2-state button
import javax.swing.BorderFactory;    // manufacturers Border objects around buttons
import javax.swing.Box;              // to create toolbar spacer
import javax.swing.UIManager;        // to access default icons
import javax.swing.JLabel;           // text or image holder
import javax.swing.ImageIcon;        // holds a custom icon
import javax.swing.JComboBox;        // for selecting from lists
import javax.swing.SwingConstants;   // useful values for Swing method calls
import javax.swing.JFileChooser;     // helps choose files on device

import javax.imageio.ImageIO;        // loads an image from a file

import java.io.File;                 // opens a file
import java.io.FileReader;           // reads a file
import java.io.FileWriter;           // writes a new file
import java.io.IOException;          // reports an error reading from a file
import java.rmi.server.ObjID;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BasicStroke;
import java.awt.BorderLayout;        // layout manager for main window
import java.awt.FlowLayout;          // layout manager for About dialog

import java.awt.Graphics;            // the graphics of a picture
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;               // the color of widgets, text, or borders
import java.awt.Font;                // rich text in a JLabel or similar widget
import java.awt.image.BufferedImage; // holds an image loaded from a file

public class MainWin extends JFrame {
    private final String NAME = "ELSA";
    private final String EXTENSION = "elsa";
    private final String VERSION = "0.4";
    private final String FILE_VERSION = "1.0";
    private final String MAGIC_COOKIE = "⮚Ě1şà⮘";
    private final String DEFAULT_STORE_NAME = "New " + NAME + " Store";

    public enum Record {CUSTOMER, OPTION, COMPUTER, ORDER};
    public MainWin(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        
        // /////// ////////////////////////////////////////////////////////////////
        // M E N U
        // Add a menu bar to the PAGE_START area of the Border Layout

        JMenuBar menubar = new JMenuBar();
        
        JMenu     file       = new JMenu("File");
        JMenuItem quit       = new JMenuItem("Quit");
        JMenuItem newStore   = new JMenuItem("New Store");
        JMenuItem open       = new JMenuItem("Open");
                  save       = new JMenuItem("Save");
                  saveAs     = new JMenuItem("Save As");

        JMenu     insert     = new JMenu("Insert");
        JMenuItem custInsert = new JMenuItem("Customer");
        JMenuItem optInsert  = new JMenuItem("Option");
        JMenuItem compInsert = new JMenuItem("Computer");
        JMenuItem ordInsert  = new JMenuItem("Order");

        JMenu     view       = new JMenu("View");
        JMenuItem custView   = new JMenuItem("Customer");
        JMenuItem optView    = new JMenuItem("Option");
        JMenuItem compView   = new JMenuItem("Computer");
        JMenuItem ordView    = new JMenuItem("Order");

        JMenu     help       = new JMenu("Help");
        JMenuItem about      = new JMenuItem("About");

        quit .addActionListener(event -> onQuitClick());
        newStore.addActionListener(event -> onNewClick());
        open.addActionListener(event -> onOpenClick());
        save.addActionListener(event -> onSaveClick());
        saveAs.addActionListener(event -> onSaveAsClick());

        custInsert.addActionListener(event -> onInsertCustomerClick());
        optInsert.addActionListener(event -> onInsertOptionClick());
        compInsert.addActionListener(event -> onInsertComputerClick());
        ordInsert.addActionListener(event -> onInsertOrderClick());

        custView.addActionListener(event -> onViewClick(Record.CUSTOMER));
        optView.addActionListener(event -> onViewClick(Record.OPTION));
        compView.addActionListener(event -> onViewClick(Record.COMPUTER));
        ordView.addActionListener(event -> onViewClick(Record.ORDER));

        about.addActionListener(event -> onAboutClick());

        
        file.add(quit);
        file.add(newStore);
        file.add(open);
        file.add(save);
        file.add(saveAs);

        insert.add(custInsert);
        insert.add(optInsert);
        insert.add(compInsert);
        insert.add(ordInsert);

        view.add(custView);
        view.add(optView);
        view.add(compView);
        view.add(ordView);
        help.add(about);
        
        menubar.add(file);
        menubar.add(insert);
        menubar.add(view);
        menubar.add(help);

        setJMenuBar(menubar);
        
        // ///////////// //////////////////////////////////////////////////////////
        // T O O L B A R
        // Add a toolbar to the PAGE_START region below the menu
        JToolBar toolbar = new JToolBar("ELSA Controls");
        
        // Create the 3 buttons using the icons provided
        //  Buttons for File
        JButton buttonNew  = new JButton(new ImageIcon("gui/anew.png"));
          buttonNew.setActionCommand("Create a new ELSA file");
          buttonNew.setToolTipText("Create a new ELSA file");
          toolbar.add(buttonNew);
          buttonNew.addActionListener(event -> onNewClick());

        JButton buttonOpen    = new JButton(new ImageIcon("gui/open.png"));
          buttonOpen.setActionCommand("Load data from selected file");
          buttonOpen.setToolTipText("Load data from selected file");
          toolbar.add(buttonOpen);
          buttonOpen.addActionListener(event -> onOpenClick());

        buttonSave    = new JButton(new ImageIcon("gui/save.png"));
          buttonSave.setActionCommand("Save data to default file");
          buttonSave.setToolTipText("Save data to default file");
          toolbar.add(buttonSave);
          buttonSave.addActionListener(event -> onSaveClick());

        buttonSaveAs  = new JButton(new ImageIcon("gui/save_as.png"));
          buttonSaveAs.setActionCommand("Save data to selected file");
          buttonSaveAs.setToolTipText("Save data to selected file");
          toolbar.add(buttonSaveAs);
          buttonSaveAs.addActionListener(event -> onSaveAsClick());

        toolbar.add(Box.createHorizontalStrut(25));

        // Button's for Insert
        JButton button1  = new JButton(new ImageIcon("gui/add_customer.png"));
          button1.setActionCommand("New Customer");
          button1.setToolTipText("Create a new customer");
          toolbar.add(button1);
          button1.addActionListener(event -> onInsertCustomerClick());

        JButton button2    = new JButton(new ImageIcon("gui/add_option.png"));
          button2.setActionCommand("New Option");
          button2.setToolTipText("Create a new option, discarding any records");
          toolbar.add(button2);
          button2.addActionListener(event -> onInsertOptionClick());

        JButton button3    = new JButton(new ImageIcon("gui/add_computer.png"));
          button3.setActionCommand("New Computer");
          button3.setToolTipText("Create a new computer, discarding any records");
          toolbar.add(button3);
          button3.addActionListener(event -> onInsertComputerClick());

        JButton button4    = new JButton(new ImageIcon("gui/add_order.png"));
          button4.setActionCommand("New Order");
          button4.setToolTipText("Create a new order, discarding any records");
          toolbar.add(button4);
          button4.addActionListener(event -> onInsertOrderClick());

        toolbar.add(Box.createHorizontalStrut(25));

        // Button's for View
        JButton button5    = new JButton(new ImageIcon("gui/view_customers.png"));
          button5.setActionCommand("View Customer");
          button5.setToolTipText("View Customer");
          toolbar.add(button5);
          button5.addActionListener(event -> onViewClick(Record.CUSTOMER));

        JButton button6    = new JButton(new ImageIcon("gui/view_options.png"));
          button6.setActionCommand("View Option");
          button6.setToolTipText("View Option");
          toolbar.add(button6);
          button6.addActionListener(event -> onViewClick(Record.OPTION));

        JButton button7    = new JButton(new ImageIcon("gui/view_computers.png"));
          button7.setActionCommand("View Computer");
          button7.setToolTipText("View Computer");
          toolbar.add(button7);
          button7.addActionListener(event -> onViewClick(Record.COMPUTER));

        JButton button8    = new JButton(new ImageIcon("gui/view_order.png"));
          button8.setActionCommand("View Order");
          button8.setToolTipText("View Order");
          toolbar.add(button8);
          button8.addActionListener(event -> onViewClick(Record.ORDER));

        getContentPane().add(toolbar, BorderLayout.PAGE_START);
        
        // /////////////////////////// ////////////////////////////////////////////
        // D I S P L A Y
        display = new JLabel();
        display.setFont(new Font("SansSerif", Font.BOLD, 14));
        display.setVerticalAlignment(JLabel.TOP);
        add(display, BorderLayout.CENTER);
        
        // Make everything in the JFrame visible
        setVisible(true);

        // Start a new store
        store = new Store("ELSA Prime");
    }
    
    // Listeners
    protected void onNewClick() {onNewClick("");}
    protected void onNewClick(String name) { 
        if(name.isEmpty()) {
            name = JOptionPane.showInputDialog(this, "Store Name", DEFAULT_STORE_NAME);
            if(name.isEmpty()) name = DEFAULT_STORE_NAME;
        }
        store = new Store(name);
        onViewClick(Record.CUSTOMER);
        setDirty(false);
    }

    protected void onOpenClick()
    {
        JFileChooser fOpen = new JFileChooser(filename);
        FileFilter storeFiles = new FileNameExtensionFilter("Store files", "store");
        fOpen.addChoosableFileFilter(storeFiles);
        fOpen.setFileFilter(storeFiles);

        int result = fOpen.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) 
        {
        filename = fOpen.getSelectedFile();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String magicCookie = br.readLine();
            if(!magicCookie.equals(MAGIC_COOKIE)) throw new RuntimeException("Not an ELSA file");
            String fileVersion = br.readLine();
            if(!fileVersion.equals(FILE_VERSION)) throw new RuntimeException("Incompatible ELSA file format");
            
            store = new Store(br);                       // Open a new store
            onViewClick(Record.CUSTOMER);                // Update the user interface
            setDirty(false);
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to open " + filename + '\n' + e, "Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void onSaveClick()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(MAGIC_COOKIE + '\n');
            bw.write(FILE_VERSION + '\n');
            store.save(bw);
            setDirty(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to open " + filename + '\n' + e, "Failed", JOptionPane.ERROR_MESSAGE);
        }
        setDirty(false);
    }

    protected void onSaveAsClick()
    {
        final JFileChooser fSaveAs = new JFileChooser(filename);
        FileFilter storeFiles = new FileNameExtensionFilter("Store files", "store");
        fSaveAs.addChoosableFileFilter(storeFiles);
        fSaveAs.setFileFilter(storeFiles);

        int result = fSaveAs.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
        filename = fSaveAs.getSelectedFile();
        if(!filename.getAbsolutePath().endsWith(".store"))
        filename = new File(filename.getAbsolutePath() + ".store");
        onSaveClick();
        }
    }

    protected void onQuitClick() {System.exit(0);}   // Exit the game

    protected void onInsertCustomerClick()
    {
        ImageIcon icon = new ImageIcon("gui/add_customer.png");
        JTextField name = new JTextField(), email = new JTextField();
        Object[] fields = {"Customer name", name, "Customer email", email};
        try {
            JOptionPane.showConfirmDialog(this, fields, "New Customer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

            String nameS = name.getText();
            String emailS = email.getText();
            store.add(new Customer(nameS, emailS));

            onViewClick(Record.CUSTOMER);
        } catch(NullPointerException e) {
        } catch(Exception e) { 
            JOptionPane.showMessageDialog(this, e, "Customer Not Created", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onInsertOptionClick()
    {
        ImageIcon icon = new ImageIcon("gui/add_option.png");
        JTextField name = new JTextField(), cost = new JTextField();
        Object[] fields = {"Option name", name, "Option cost", cost};
        try {
            JOptionPane.showConfirmDialog(this, fields, "New Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

            String nameS = name.getText();
            String costS = cost.getText();
            long costL = (long) (100.0 * Double.parseDouble(costS));

            store.add(new Option(nameS, costL));

            onViewClick(Record.OPTION);
        } catch(NullPointerException e) {
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Customer Not Created", JOptionPane.ERROR_MESSAGE);
        }
    }
            
    protected void onInsertComputerClick()
    {
        ImageIcon icon = new ImageIcon("gui/add_computer.png");
        JTextField name = new JTextField(), model = new JTextField();
        Object[] fields = {"Computer name", name, "Computer model", model};
        try { 
            JOptionPane.showConfirmDialog(this, fields, "New Computer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

            String nameS = name.getText();
            String modelS = model.getText();

            Computer c = new Computer(nameS, modelS);

            JComboBox<Object> cb = new JComboBox<>(store.options());
            int optionsAdded = 0;
            while(true) {
                int button = JOptionPane.showConfirmDialog(this, cb, 
                    "Another Option?", JOptionPane.YES_NO_OPTION);
                if(button != JOptionPane.YES_OPTION) break;
                c.addOption((Option) cb.getSelectedItem());
                ++optionsAdded;
            }
            if(optionsAdded > 0) {
                store.add(c);
                onViewClick(Record.COMPUTER);
                setDirty(true);
            }
        } catch(NullPointerException e) {
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Computer Not Created", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onInsertOrderClick()
    {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon("gui/add_order.png");
        } catch(Exception e) {
        }
        
        try{
            Object[] customers = store.customers();
            Object[] computers = store.computers();

            Object currentCustomer = null;
            Order order = new Order((Customer) currentCustomer);
            if (customers == null)
            {
                onInsertCustomerClick();
                customers = store.customers();
                if(customers.length == 0) return;
            } 

            Customer customer = (Customer) customers[0]; 

            if(customers.length > 1) {
                JLabel label = new JLabel("Order for which Customer?");
                JComboBox<Object> cb = new JComboBox<>(customers);
                int button = JOptionPane.showConfirmDialog(this, new Object[]{label, cb}, "New Order", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                if(button != JOptionPane.OK_OPTION) return;
                customer = (Customer) cb.getSelectedItem();
            }

            int computersAdded = 0;
            while (true)
            {
                JComboBox<Object> computerComboBox = new JComboBox<>(computers);
                int result = JOptionPane.showConfirmDialog(null, new Object[] 
                {"Current Order: " + order.toString() + "\nSelect a Computer to add to the order:", computerComboBox}, 
                "Add Computer to Order", JOptionPane.OK_CANCEL_OPTION);
                if(result != JOptionPane.YES_OPTION) 
                    break;
                
                order.addComputer((Computer) computerComboBox.getSelectedItem());
                ++computersAdded;
            }
            
            if(computersAdded > 0) {
                store.add(order);
                onViewClick(Record.ORDER);
                setDirty(true);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Order Not Created", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onViewClick(Record record)
    {
        String header = null;
        Object arr[] = null;

        if(record == Record.CUSTOMER) {
            header = "Customers who have purchased from us!";
            arr = store.customers();
        }
        if(record == Record.OPTION) {
           header = "Option choices for our SuperComputers";
           arr = store.options();
        }
        if(record == Record.COMPUTER) {
            header = "Computers for Sale";
            arr = store.computers();
        }
        if(record == Record.ORDER) {
            header = "Past and Current Orders";
            arr = store.orders();
        }
        
        StringBuilder sb = new StringBuilder("<html><p><font size=+2>" + header + "</font></p><br/>\n<ol>\n");
        for(Object i : arr) sb.append("<li>" + i.toString().replaceAll("<","&lt;").replaceAll(">", "&gt;")
        .replaceAll("\n", "<br/>") + "</li>\n");
        sb.append("</ol></html>");
        display.setText(sb.toString());
    }

    protected void onAboutClick() {
        Canvas logo = new Canvas("gui/store-icons.png");

        JLabel title = new JLabel("<html>"
          + "<p><font size=+4>" + NAME + "</font></p>"
          + "</html>",
          SwingConstants.CENTER);

        JLabel subtitle = new JLabel("<html>"
          + "<p>Exceptional Laptops and Supercomputers Always</p>"
          + "</html>",
          SwingConstants.CENTER);

        JLabel version = new JLabel("<html>"
          + "<p>Version " + VERSION + "</p>"
          + "</html>",
          SwingConstants.CENTER);

        JLabel artists = new JLabel("<html>"
          + "<br/><p>Copyright 2017-2023 by George F. Rice</p>"
          + "<p>Licensed under Gnu GPL 3.0</p/p><br/>"

          + "<br/><p>Logo based on work by Clker-Free-Vector-Images per the Pixabay License</p>"
          + "<p><font size=-2>https://pixabay.com/vectors/internet-www-mouse-web-business-42583</font></p>"
          + "<br/><p>Add Customer icon based on work by Dreamstate per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/user_3114957</font></p>"
          + "<br/><p>View Customers icon based on work by Ilham Fitrotul Hayat per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/group_694642</font></p>"
          + "<br/><p>Add and View Options icons based on work by Freepik per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/quantum-computing_4103999</font></p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/edge_8002173</font></p>"
          + "<br/><p>Add Computer icon based on work by Freepik per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/laptop_689396</font></p>"
          + "<br/><p>View Computers icon based on work by Futuer per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/computer-networks_9672993</font></p>"
          + "<br/><p>Add Order icon based on work by Kiranshastry per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/shopping-cart_711897</font></p>"
          + "<br/><p>View Orders icon based on work by Freepik per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/to-do-list_1950715</font></p>"
          + "<br/><p>New and open icon based on work by IconKanan per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/share_2901214</font></p>"
          + "<br/><p>Save and Save As icons based on work by mavadee per the Flaticon License</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icon/download_3580085</font></p>"

          + "<br/><p>Method onViewClick(), and some parts of onInsertCustomerClick(), onInsertOptionClick(),</p>"
          + "<p>and onInsertComputerClick() and the icons are taken from the professor's work!</p>"
          + "</html>");
          
         JOptionPane.showMessageDialog(this, 
             new Object[]{logo, title, subtitle, version, artists},
             "ELSA", JOptionPane.PLAIN_MESSAGE);
    }

    private void setDirty(boolean isDirty) {
        save.setEnabled(isDirty);
        saveAs.setEnabled(isDirty);
        buttonSave.setEnabled(isDirty);
        buttonSaveAs.setEnabled(isDirty);
    };
    
    private Store store;                    // The current Elsa store    
    private JLabel display;                 // Display page of date

    private File filename;

    private JMenuItem save;
    private JMenuItem saveAs;
    private JButton buttonSave;
    private JButton buttonSaveAs;
}