package ca.bjad.stubwebserver.model;

/**
 * HTTP Status event used to state when the server is started
 * and stopped within the event table.
 *
 * @author 
 *   Ben Dougall
 */
public class HTTPStatusEvent extends AbstractEventBean
{
   boolean startEvent = false;
   
   /**
    * Default Constructor
    */
   public HTTPStatusEvent()
   {
      this(false);
   }
   
   /**
    * Constructor setting the start/stop flag
    * for the event.
    * 
    * @param startEvent
    *    True if this event is for the server starting, false if
    *    this is for the server stopping.
    */
   public HTTPStatusEvent(boolean startEvent)
   {
      this.startEvent = startEvent;
   }   

   /**
    * Returns the value of the HTTPStatusEvent instance's 
    * startEvent property.
    *
    * @return 
    *   The value of startEvent
    */
   public boolean isStartEvent()
   {
      return this.startEvent;
   }

   /**
    * Sets the value of the HTTPStatusEvent instance's 
    * startEvent property.
    *
    * @param startEvent 
    *   The value to set within the instance's 
    *   startEvent property
    */
   public void setStartEvent(boolean startEvent)
   {
      this.startEvent = startEvent;
   }

   @Override
   public String getDescriptionText()
   {
      return "HTTP Server has been " + (startEvent ? "Started" : "Stopped");
   }

   @Override
   public String getEventType()
   {
      return "Status";
   }
   
}
