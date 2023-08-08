package ca.bjad.stubwebserver.listeners;

/**
 * Listener interface for when the stop button is pressed
 * within the configuration panel. 
 *
 * @author 
 *   Ben Dougall
 */
public interface StopButtonTriggeredListener
{
   /**
    * Method to implement to react to the stop button being
    * pressed in the config panel.
    */
   public void stopServerPressed();
}
