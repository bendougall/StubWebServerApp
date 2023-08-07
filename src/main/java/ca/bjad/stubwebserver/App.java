package ca.bjad.stubwebserver;

import javax.swing.SwingUtilities;

import ca.bjad.stubwebserver.ui.MainWindow;

/**
 * The launch point for the application, starting the window. 
 *
 * @author 
 *   Ben Dougall
 */
public class App implements Runnable
{
   
   /**
    * Launching point for the application. 
    * 
    * @param args
    *    The command line arguments, which are not 
    *    used for the application.
    */
   public static void main(String[] args)
   {
      SwingUtilities.invokeLater(new App());
   }

   @Override
   public void run()
   {
      MainWindow window = new MainWindow();
      window.setVisible(true);
   }
}
