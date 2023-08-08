package ca.bjad.stubwebserver.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import ca.bjad.stubwebserver.HTTPServerManager;
import ca.bjad.stubwebserver.listeners.HTTPServerStateListener;
import ca.bjad.stubwebserver.listeners.StartButtonTriggeredListener;
import ca.bjad.stubwebserver.listeners.StopButtonTriggeredListener;

/**
 * The panel with the controls allow for configuration of the 
 * web server the application will be hosting.  
 *
 * @author 
 *   Ben Dougall
 */
public class ConfigPanel extends JPanel implements ActionListener, HTTPServerStateListener
{
   private static final long serialVersionUID = -558087299611988382L;

   private JTextField portNumberField = new JTextField(7);
   private JButton startButton = new JButton("Start");
   private JButton stopButton = new JButton("Stop");
   
   private Set<StartButtonTriggeredListener> startButtonListeners = new LinkedHashSet<>();
   private Set<StopButtonTriggeredListener> stopButtonListeners = new LinkedHashSet<>();
   
   
   /**
    * Constructor, initializing and constructing the panel
    * for the configuration options for the web server
    * application. 
    */
   public ConfigPanel()
   {
      super(true);
      setLayout(new BorderLayout(5, 5));
           
      add(createPortNumberPanel(), BorderLayout.WEST);
      
      // Add a filler label for the center of the panel.
      add(new JLabel(), BorderLayout.CENTER);
      
      add(createButtonPanel(), BorderLayout.EAST);
      
      // add in the HTTP Server state listener to react to 
      // the server starting and stopping
      HTTPServerManager.instance().addHTTPServerStateListener(this);
   }
   
   /**
    * Returns the port number entered on the panel if its valid. 
    * @return
    *    The port number, if valid, as an int
    * @throws IllegalStateException
    *    Thrown if the value in the text field is not a valid
    *    integer between 1 and 65535.
    */
   public int getPortNumber() throws IllegalStateException
   {
      try
      {
         int val = Integer.parseInt(portNumberField.getText());
         if (val > 0 && val < 65536)
         {
            return val;
         }
         else
         {
            throw new IllegalStateException("Port Number must be a valid integer between 1 and 65535");
         }
      }
      catch (NumberFormatException e)
      {
         throw new IllegalStateException("Port number must be entered, and be a valid integer between 1 and 65535.");
      }
   }
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (startButton.equals(e.getSource()))
      {
         onServerStarted(-1);
         try
         {
            int port = getPortNumber();
            fireStartButtonPressedListeners(port);
         }
         catch (IllegalStateException ex)
         {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not start server", JOptionPane.WARNING_MESSAGE);
            onServerStopped();
         }
      }
      else if (stopButton.equals(e.getSource()))
      {
         fireStopButtonPressedListeners();
      }
   }
   
   /**
    * Adds a start button triggered listener to the panel. 
    * @param listener
    *    The listener to add
    * @return
    *    True if the listener was added, false otherwise (duplicate).
    */
   public boolean addStartButtonListener(StartButtonTriggeredListener listener)
   {
      return this.startButtonListeners.add(listener);
   }
   
   /**
    * Removes a start button triggered listener from the panel.
    * @param listener
    *    The listener to remove
    * @return
    *    True if the listener was removed, false otherwise.
    */
   public boolean removeStartButtonListener(StartButtonTriggeredListener listener)
   {
      return this.startButtonListeners.remove(listener);
   }
   
   /**
    * Adds a stop button triggered listener to the panel. 
    * @param listener
    *    The listener to add
    * @return
    *    True if the listener was added, false otherwise (duplicate).
    */
   public boolean addStopButtonListener(StopButtonTriggeredListener listener)
   {
      return this.stopButtonListeners.add(listener);
   }
   
   /**
    * Removes a stop button triggered listener from the panel. 
    * @param listener
    *    The listener to remove
    * @return
    *    True if the listener was removed, false otherwise.
    */
   public boolean removeStopButtonListener(StopButtonTriggeredListener listener)
   {
      return this.stopButtonListeners.remove(listener);
   }
   
   /**
    * Fires the start server pressed listeners with the port number
    * entered by the user in the text field. Note: This will always
    * fire on the EDT.
    * 
    * @param portNumber
    *    The port number from the text field.
    */
   protected final void fireStartButtonPressedListeners(int portNumber)
   {
      Runnable r = new Runnable()
      {         
         @Override
         public void run()
         {
            for (StartButtonTriggeredListener listener : startButtonListeners)
            {
               listener.startServerPressed(portNumber);
            }
         }
      };
      if (SwingUtilities.isEventDispatchThread())
      {
         r.run();
      }
      else
      {
         SwingUtilities.invokeLater(r);
      }
   }
   
   /**
    * Fires the stop server pressed event to the listeners.
    * Note: This will always fire on the EDT.
    */
   protected final void fireStopButtonPressedListeners()
   {
      Runnable r = new Runnable()
      {         
         @Override
         public void run()
         {
            for (StopButtonTriggeredListener listener : stopButtonListeners)
            {
               listener.stopServerPressed();
            }
         }
      };
      if (SwingUtilities.isEventDispatchThread())
      {
         r.run();
      }
      else
      {
         SwingUtilities.invokeLater(r);
      }
   }
   
   private JPanel createPortNumberPanel()
   {
      // Set the default properties for the controls.
      portNumberField.setText("8686");
      
      JPanel pane = new JPanel(new FlowLayout(SwingConstants.LEFT, 8, 0), true);
      
      // Add an empty border to VAlign the label and text box with the buttons.
      pane.setBorder(new EmptyBorder(2, 0, 0, 0));
      pane.add(new JLabel("Port Number:"));
      pane.add(portNumberField);
      
      return pane;
   }
   
   private JPanel createButtonPanel()
   {
      // Disable the stop button by default.
      stopButton.setEnabled(false);
      
      // Add listeners
      startButton.addActionListener(this);
      stopButton.addActionListener(this);
      
      // Create the inner panel for thje buttons.
      JPanel pane = new JPanel(new GridLayout(1, 3, 5, 5), true);
      pane.add(startButton);
      pane.add(stopButton);
      return pane;
   }

   @Override
   public void onServerStarted(int portNumber)
   {
      if (portNumber > -1)
      {
         portNumberField.setText(String.valueOf(portNumber));
      }
      
      portNumberField.setEnabled(false);
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
   }

   @Override
   public void onServerStopped()
   {
      portNumberField.setEnabled(true);
      startButton.setEnabled(true);
      stopButton.setEnabled(false);
   }
}
