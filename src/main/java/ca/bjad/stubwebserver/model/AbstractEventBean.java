package ca.bjad.stubwebserver.model;

import java.util.Date;

/**
 * Abstract event bean used to show details
 * in the event table within the UI
 *
 * @author 
 *   Ben Dougall
 */
public abstract class AbstractEventBean
{
   private Date timestamp = new Date();
   
   /**
    * Returns the value of the AbstractEventBean instance's 
    * timestamp property.
    *
    * @return 
    *   The value of timestamp
    */
   public Date getTimestamp()
   {
      return this.timestamp;
   }
   
   /**
    * Sets the value of the AbstractEventBean instance's 
    * timestamp property.
    *
    * @param timestamp 
    *   The value to set within the instance's 
    *   timestamp property
    */
   public void setTimestamp(Date timestamp)
   {
      this.timestamp = timestamp;
   }
   /**
    * Returns the text to show as the description for 
    * the event. 
    * 
    * @return
    *    The description text to show in the event table.
    */
   public abstract String getDescriptionText();
   
   /**
    * Returns the text to show as the event type for the event.
    * 
    * @return
    *    The event type text to show in the event type.   
    */
   public abstract String getEventType();
}
