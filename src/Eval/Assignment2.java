/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eval;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author ztmvf2
 */
public class Assignment2 
{

    /**
     * @param args the command line arguments
     */

    public static void main(String args[])
    {
        // check command-line arguments
      //if ( args.length == 2 )
      //{
         // get command-line arguments
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver";
        //String databaseDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String databaseURL = "jdbc:derby://localhost:1527/rerun";

         // create new Eval
         Eval eval = new Eval( databaseDriver, databaseURL );
         eval.createUserInterface();
         eval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
      //}
      //else // invalid command-line arguments
      //{
      //   System.out.println( "Usage: java EVAL needs databaseDriver databaseURL" );
      //}
    }
/**
 *
 * @author brilaw
 */
    public static class Eval extends JFrame implements ActionListener
    {
    //DECLARE THE ELEMENTS OR OBJECTS THAT YOU WILL PUT IN YOUR FRAME
    //NOTICE HOW A PANEL IS CREATED FOR EACH ONE THIS WILL MAKE IT EASIER BUILD
   
    public JLabel teamLabel;
    private JComboBox teamComboBox;
    public JComboBox imagesComboBox;
    private JPanel teamPanel;
    private JPanel buttonPanel;
    private JButton submitButton;
   
   //these are fields that will be used to hold the values pulled from the interface
    String teamname;
    int q1;
    int q2;
    int q3;
    int q4;
    String comments;
    double teamavg;
    
    
    
    // instance variables used to manipulate database
   private Connection myConnection;
   private Statement myStatement;
   private ResultSet myResultSet;
          
   
  
   //MAIN METHOD: NOTICE WE TAKE IN THE ARGUMENTS THAT ARE
   //PASSED IN AND INSTANTIATE OUR CLASS WITH THEM

   
    //CONSTRUCTOR: WE SET UP OUR DATABASE HERE THEN MAKE A CALL
    //TO A FUNCTION CALLED CREATEUSERINTERFACE TO BUILD OUR GUI
    public Eval(String databaseDriver, String databaseURL)
    {
        // establish connection to database
      try
      {
         // load Sun driver
         //Class.forName( databaseDriver );
         DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
         // connect to database
         myConnection = DriverManager.getConnection( databaseURL );

         // create Statement for executing SQL
         myStatement = myConnection.createStatement();
      }
      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
      //catch ( ClassNotFoundException exception )
     // {
      //   exception.printStackTrace();
      //}
        // set up accountNumberJComboBox
     
       
     // createUserInterface(); // set up GUI
     
     

     
    }
   

    private void createUserInterface()
    {
      // get content pane for attaching GUI components
      Container contentPane = getContentPane();
       
      contentPane.setLayout( null ); 
      
      // INSTRUCTOR COMBO BOX SET UP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // set up Instructor Panel
      
      // enable explicit positioning of GUI components
      
      teamPanel = new JPanel();
      teamPanel.setBounds(40, 20, 276, 48 );
      teamPanel.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel.setLayout( null );
      contentPane.add( teamPanel );

      // set up Instructor Label
      teamLabel = new JLabel();
      teamLabel.setBounds( 25, 15, 100, 20 );
      teamLabel.setText( "Teams:" );
      teamPanel.add( teamLabel );

      // set up accountNumberJComboBox
      teamComboBox = new JComboBox();
      teamComboBox.setBounds( 150, 15, 96, 25 );
      teamComboBox.addItem( "" );
      teamComboBox.setSelectedIndex( 0 );
      teamPanel.add( teamComboBox ); 
      
      
    
      buttonPanel = new JPanel();
      buttonPanel.setBounds(40, 100, 276, 60);
      buttonPanel.setBorder( BorderFactory.createEtchedBorder() );
      buttonPanel.setLayout(null);
      
      submitButton = new JButton("Submit");
      submitButton.setBounds(50, 15, 78, 40);
      buttonPanel.add(submitButton);
      contentPane.add(buttonPanel);
      
      submitButton.addActionListener(this);
      //teamComboBox.addItemListener(this);
      
      loadTeams();
      
      
      setTitle( "EVAL" );   // set title bar string
      setSize( 375, 410 ); // set window size
      setVisible( true );  // display window
    }

   
    private void loadTeams()
    {
         // get all account numbers from database
      try
      {
        
          myResultSet = myStatement.executeQuery( "SELECT DISTINCT TEAMNAME FROM APP.TEAMS");
       
         while ( myResultSet.next() )
         {
               teamComboBox.addItem(myResultSet.getString( "TEAMNAME" ) );
         }

         myResultSet.close(); // close myResultSet
        

      } // end try

      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
    }

    @Override
   public void actionPerformed(ActionEvent event)
    {
        
        JOptionPane.showMessageDialog( null, "You pressed: " + teamComboBox.getSelectedItem().toString() );
        //Object obj = teamComboBox.g
        //teamname = teamComboBox.getSelectedItem().toString();
        //int x = teamComboBox.getSelectedIndex();
       // q1 = 5;
        //q2 = 3;
        //updateTeams();
        //System.out.println(teamComboBox.getSelectedIndex() + "     " + (String)teamComboBox.getSelectedItem());

    }
   
   // @Override
   /* public void itemStateChanged(ItemEvent event)
    {
       
        if ( event.getSource() == rb1 && event.getStateChange() == ItemEvent.SELECTED)
        {
            q1 = Integer.parseInt(rb1.getText());
        }
        else if (event.getSource() == rb2 && event.getStateChange() == ItemEvent.SELECTED)
        {
            q1 = Integer.parseInt(rb2.getText());
        }
        else if (event.getSource() == rb3 && event.getStateChange() == ItemEvent.SELECTED)
        {
           q1 = Integer.parseInt(rb3.getText());
        }
        else if( event.getSource() == rb1 && event.getStateChange() == ItemEvent.DESELECTED)
        {
            JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
        }
    }*/

    private void updateTeams()
   {
      // update balance in database
      try
      {
       
          //
          String sql = "UPDATE APP.TEAMS SET Q1TECH = " + q1 + "," + "Q2CLARITY = " + q2 + " WHERE " +
                       "TEAMNAME = " + "'" + teamname + "'";
          //String sql2 =  "UPDATE APP.TEAMEVAL" + " SET q2 = " + q2 + " WHERE " +
          //             "TEAMNAME = '" + myteamname + "'" + "and course = '" + courseName + "'";
          myStatement.executeUpdate(sql);
          //myStatement.executeUpdate(sql2);
         
      }
      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
   }

    
   public void itemStateChanged( ItemEvent event )
   {
//        if ( event.getStateChange() == ItemEvent.SELECTED )
//        {
//                int x = teamComboBox.getSelectedIndex();
//        }
   }

    
}
    
}
