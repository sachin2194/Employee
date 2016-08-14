/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import com.sun.management.jmx.Trace;
import java.awt.Color;	
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author SACHIN
 */
public class Employee extends JFrame implements ActionListener{
    
    //adding components
    Container cpane;
    JCheckBox egg,coffee,toast,steak,potato,wine,cake;
    
    JLabel Emp_id,Emp_name,Emp_bal,Emp_date,L_Food,Type;
    JTextField e_txt_id,e_txt_name,e_txt_bal,e_txt_date;
    JButton btninsert,btnshowall,btnupdate,btncheck;
    JRadioButton btn_morning,btn_night;
    ButtonGroup bg;
    JTextArea view;
    Connection con;
    PreparedStatement psshow,psinsert,psupdate;
    Statement stmt;
    ResultSet rs;
    
    
    //Constructor Employee
    Employee()
    {
        //setting window and layout
        cpane=getContentPane();
        cpane.setBackground(Color.GRAY);
        cpane.setLayout(new FlowLayout());
        addControls();
        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //exeception handling and databse connection
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","root","1234");
            psshow=con.prepareStatement(
                    "select * from Employee where Id No=?");
            psinsert=con.prepareStatement(
                    "insert into Employee values(?,?,?,?,?)");            
psupdate=con.prepareStatement("update Employee set EmployeeName=?,Balance=?,Date=?, where Id No=?");
            stmt=con.createStatement();
        }
        catch(Exception ex){}
    }
    
    //adding the controls to the window
    private void addControls()
    {
       
           
        Emp_id=new JLabel("Id No");
        Emp_name=new JLabel("Name");
        Emp_bal=new JLabel("Balance");
        Emp_date=new JLabel("Date");
        L_Food = new JLabel("Food");
        Type =new JLabel("Type");
        e_txt_id=new JTextField(5);
        e_txt_name=new JTextField(25);
        e_txt_bal=new JTextField(10);
        e_txt_date=new JTextField(10);
        
        //button names
        btnupdate=new JButton("Update");
        btninsert=new JButton("Insert");
        btnshowall=new JButton("ShowAll");
        btncheck=new JButton("Check");
        btn_morning = new JRadioButton("Morning");
        btn_night = new JRadioButton("Night");
        view=new JTextArea(10,20);
        
        egg=new JCheckBox("Egg");
        coffee=new JCheckBox("coffee");
        toast= new JCheckBox("Toast");
        steak=new JCheckBox("Steak");
        potato=new JCheckBox("Potato");
        wine=new JCheckBox("Wine");
        cake=new JCheckBox("Cake");
        
// adding to window panel
        cpane.add( Emp_id);
        cpane.add(e_txt_id);
        cpane.add( Emp_name);
        cpane.add(e_txt_name);
        cpane.add( Emp_bal);
        cpane.add(e_txt_bal);
        cpane.add( Emp_date);
        cpane.add(e_txt_date);
        cpane.add(btninsert);
        cpane.add(btnshowall);
        cpane.add(btnupdate);
        cpane.add(btncheck);
        cpane.add(Type);
        bg=new ButtonGroup();
        bg.add(btn_morning); 
        bg.add(btn_night);
        add(btn_morning);
        add(btn_night);
        cpane.add(L_Food);
        cpane.add(egg);
        cpane.add(coffee);
        cpane.add(toast);
        cpane.add(steak);
        cpane.add(potato);
        cpane.add(wine);
        cpane.add(cake);    
        cpane.add(view);  

       
        
              
               //condition to visible checkbox
      if(btn_morning.isSelected()){
          
      
       btn_morning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                egg.setEnabled(true);
                toast.setEnabled(true);
                coffee.setEnabled(true);             
                               
            }
        });
       } 
      else{
       btn_night.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
               steak.setEnabled(true);
                wine.setEnabled(true);
                cake.setEnabled(true);
                potato.setEnabled(true);
            }
        });
      }
      //button click event
       btninsert.addActionListener(this);
        btnshowall.addActionListener(this);
        btnupdate.addActionListener(this);
        btncheck.addActionListener(this);
        
    }
    
    //button action performed
    public void actionPerformed(ActionEvent ae)
    {
        String str=ae.getActionCommand();
        try{
            //inserting data
             if(str=="Insert")
            {
                int rn=Integer.parseInt(e_txt_id.getText());
                String nm=e_txt_name.getText();
                int bal=Integer.parseInt(e_txt_bal.getText());               
                           
                psinsert.setInt(1,rn);
                psinsert.setString(2,nm);
                psinsert.setInt(3,bal);         
                psinsert.executeUpdate();
                
              
                
            }
            //updating
            else if(str=="Update")
            {
                int rn=Integer.parseInt(e_txt_id.getText());
                String nm=e_txt_name.getText();          
                int bal=Integer.parseInt(e_txt_bal.getText());    
                               
                psupdate.setInt(5,rn);
                psupdate.setString(1,nm);
                psupdate.setInt(2,bal);
                psupdate.executeUpdate();
            }
            
            //display details
            else if(str=="ShowAll")
            {
                rs = stmt.executeQuery("select * from Employee");
                String str1="IdNo EmployeeName Balance Date\n";
                while (rs.next())
                {
                    str1+=rs.getInt("IdNo")+" "+rs.getString("EmployeeName")
                        +" "+rs.getInt("Balance")+""+rs.getInt("Date")+"\n";
                    view.setText(str);
                }
            }
            
            //check status
            else if(str=="check")
            {
                rs=stmt.executeQuery("select * from Employee where Balance>20000");
                String str1="IdNo EmployeeName Balance Date\n";
                while (rs.next())
                {
                    str1+=rs.getInt("IdNo")+" "+rs.getString("EmployeeName")
                        +" "+rs.getInt("Balance")+"\n";
                    view.setText(str);
                }
            }
            
        }
        catch(Exception e){}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //calling employee
        Employee emp=new Employee();
        // TODO code application logic here
    }

    
    
    
}
