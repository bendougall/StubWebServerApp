package ca.bjad.stubwebserver.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * The main window for the application. 
 *
 * @author 
 *   Ben Dougall
 */
public class MainWindow extends JFrame 
{
   private static final long serialVersionUID = 528718104450657104L;
   
   /**
    * Minimum size for the window (and default size for the time being).
    */
   private static final Dimension MINIMUM_SIZE = new Dimension(800, 600);
   
   /**
    * Constructor, setting up the window, the controls, and 
    * the listeners for the application.
    */
   public MainWindow()
   {
      super("BJAD Stubbed Web Server Application - Not Started");
      setSize(MINIMUM_SIZE);
      setMinimumSize(MINIMUM_SIZE);
      
      addWindowListener(createWindowListener());
   }

   /**
    * Creates the window listener that will handle when the user
    * wants to the close the window, and when the window is 
    * actually closed.
    * 
    * @return
    *    The window listener to handle the close events.
    */
   private WindowListener createWindowListener()
   {
      final MainWindow window = this;
      return new WindowAdapter()
      {
         /**
          * What to do when the user clicks the close 
          * icon or ALT+F4 on the window.
          */
         @Override
         public void windowClosing(WindowEvent e)
         {
            window.dispose();
         }

         /**
          * What to do when the window is actually closed.
          */
         @Override
         public void windowClosed(WindowEvent e)
         {
            // Exit the application with the successful return code.
            System.exit(0);
         }
      };
   }
}
