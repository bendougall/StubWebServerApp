package ca.bjad.stubwebserver.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * (Description)
 *
 *
 * @author 
 *   bendo
 */
public class HTTPRequestEvent extends AbstractEventBean
{
   private String endpoint = "";
   private String method = "";
   private String requestor = "";
   private Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();

   @Override
   public String getDescriptionText()
   {
      return String.format("%s :: Header Count: %s", getEndpoint(), getHeaders().size());
   }

   @Override
   public String getEventType()
   {
      return getMethod();
   }

   /**
    * Returns the value of the HTTPRequestEvent instance's 
    * endpoint property.
    *
    * @return 
    *   The value of endpoint
    */
   public String getEndpoint()
   {
      return this.endpoint;
   }

   /**
    * Sets the value of the HTTPRequestEvent instance's 
    * endpoint property.
    *
    * @param endpoint 
    *   The value to set within the instance's 
    *   endpoint property
    */
   public void setEndpoint(String endpoint)
   {
      this.endpoint = endpoint;
   }

   /**
    * Returns the value of the HTTPRequestEvent instance's 
    * method property.
    *
    * @return 
    *   The value of method
    */
   public String getMethod()
   {
      return this.method;
   }

   /**
    * Sets the value of the HTTPRequestEvent instance's 
    * method property.
    *
    * @param method 
    *   The value to set within the instance's 
    *   method property
    */
   public void setMethod(String method)
   {
      this.method = method;
   }

   /**
    * Returns the value of the HTTPRequestEvent instance's 
    * requestor property.
    *
    * @return 
    *   The value of requestor
    */
   public String getRequestor()
   {
      return this.requestor;
   }

   /**
    * Sets the value of the HTTPRequestEvent instance's 
    * requestor property.
    *
    * @param requestor 
    *   The value to set within the instance's 
    *   requestor property
    */
   public void setRequestor(String requestor)
   {
      this.requestor = requestor;
   }

   /**
    * Returns the value of the HTTPRequestEvent instance's 
    * headers property.
    *
    * @return 
    *   The value of headers
    */
   public Map<String, List<String>> getHeaders()
   {
      return this.headers;
   }

}
