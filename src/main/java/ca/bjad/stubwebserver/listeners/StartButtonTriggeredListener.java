package ca.bjad.stubwebserver.listeners;

/**
 * Listener interface for when the start button is pressed
 * within the configuration panel. 
 *
 * @author 
 *   Ben Dougall
 */
public interface StartButtonTriggeredListener
{
   /**
    * Method to implement to react to the start button being
    * pressed in the config panel. 
    * 
    * @param portNumber
    *    The port number that was entered in the config panel.
    */
   public void startServerPressed(int portNumber);
}
