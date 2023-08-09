package ca.bjad.stubwebserver.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.bjad.stubwebserver.HTTPServerManager;
import ca.bjad.stubwebserver.listeners.HTTPServerStateListener;
import ca.bjad.stubwebserver.listeners.StartButtonTriggeredListener;
import ca.bjad.stubwebserver.listeners.StopButtonTriggeredListener;

/**
 * The main window for the application. 
 *
 * @author 
 *   Ben Dougall
 */
public class MainWindow extends JFrame implements StartButtonTriggeredListener, StopButtonTriggeredListener, HTTPServerStateListener
{
   private static final long serialVersionUID = 528718104450657104L;
   private static final String TITLE_PREFIX = "BJAD Stubbed Web Server Application";
   /**
    * Minimum size for the window (and default size for the time being).
    */
   private static final Dimension MINIMUM_SIZE = new Dimension(800, 600);
   
   private ConfigPanel configPanel = null;
   private EventPanel eventPanel = null;
   
   /**
    * Constructor, setting up the window, the controls, and 
    * the listeners for the application.
    */
   public MainWindow()
   {
      super(TITLE_PREFIX + " - Not Started");
      setSize(MINIMUM_SIZE);
      setMinimumSize(MINIMUM_SIZE);
      setContentPane(createContentPane());
      
      addWindowListener(createWindowListener());
      HTTPServerManager.instance().addHTTPServerStateListener(this);
   }

   /**
    * Initializes the controls for the window and creates the new
    * content pane for the application. 
    * 
    * @return
    *    The constructed content pane for the window to display.
    */
   private JPanel createContentPane()
   {
      JPanel contentPane = new JPanel(new BorderLayout(5, 5), true);
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      
      configPanel = new ConfigPanel();
      configPanel.addStartButtonListener(this);
      configPanel.addStopButtonListener(this);
      contentPane.add(configPanel, BorderLayout.NORTH);
      
      eventPanel = new EventPanel();
      contentPane.add(eventPanel, BorderLayout.CENTER);
      
      return contentPane;
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

   @Override
   public void onServerStarted(int portNumber)
   {
      setTitle(TITLE_PREFIX + " - Running under port " + portNumber);
   }

   @Override
   public void onServerStopped()
   {
      setTitle(TITLE_PREFIX + " - Not Started");
   }

   @Override
   public void startServerPressed(int portNumber)
   {
      HTTPServerManager.instance().startServer(portNumber);      
   }
   
   @Override
   public void stopServerPressed()
   {
      HTTPServerManager.instance().stopServer();      
   }

}
