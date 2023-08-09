package ca.bjad.stubwebserver.listeners;

import ca.bjad.stubwebserver.model.AbstractEventBean;

/**
 * Listener for http server events happening within 
 * the stub server. 
 *
 * @author 
 *   Ben Dougall
 */
public interface HTTPEventListener
{
   /**
    * Method to implement in order to react to HTTP server events 
    * within the application. 
    * 
    * @param eventDetails
    *    The event details to react to.
    */
   public void httpServerEventOccurred(AbstractEventBean eventDetails);
}
