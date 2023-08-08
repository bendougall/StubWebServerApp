package ca.bjad.stubwebserver.listeners;

/**
 * Listener for used to publish the HTTP server events 
 * for when it starts or stops.
 *
 *
 * @author 
 *   Ben Dougall
 */
public interface HTTPServerStateListener
{
   /**
    * Method to implement to react when the server is started
    * @param portNumber
    *    The port the server was started on.
    */
   public void onServerStarted(int portNumber);
   
   /**
    * Method to implement to react when the server is stopped
    */
   public void onServerStopped();
}
