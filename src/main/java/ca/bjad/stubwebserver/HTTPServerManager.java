package ca.bjad.stubwebserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.SwingUtilities;

import ca.bjad.stubwebserver.listeners.HTTPEventListener;
import ca.bjad.stubwebserver.listeners.HTTPServerStateListener;
import ca.bjad.stubwebserver.model.AbstractEventBean;
import ca.bjad.stubwebserver.model.HTTPRequestEvent;
import ca.bjad.stubwebserver.model.HTTPStatusEvent;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * The class that will manage the HTTP server the application 
 * is running. 
 *
 * @author 
 *   Ben Dougall
 */
@SuppressWarnings("restriction")
public class HTTPServerManager
{
   /**
    * The flag stating if the server is started or not.
    */
   protected boolean serverStarted = false;
   /**
    * The port number the server is running under.
    */
   protected int portNumber = 0;
   /**
    * The listeners for the HTTP State events.
    */
   protected Set<HTTPServerStateListener> stateListeners = new LinkedHashSet<HTTPServerStateListener>();
   /**
    * The listeners for the HTTP events.
    */
   protected Set<HTTPEventListener> eventListeners = new LinkedHashSet<HTTPEventListener>();
   
   private HttpServer httpServer;   
   
   /**
    * Singleton holder for the manager class to implement the 
    * java lazy initialization singleton pattern for thread safety
    *
    * @author 
    *   Ben Dougall.
    */
   private static class SingletonHelper
   {
      private static final HTTPServerManager instance = new HTTPServerManager();
   }
   
   /**
    * Provides the singleton instance of the HTTPServerManager.
    * 
    * @return
    *    The singleton instance of the AppWideEventManager.
    */
   public static HTTPServerManager instance()
   {
      return SingletonHelper.instance;
   }
   
   /**
    * Attempts to start the http server using the port number 
    * provided.
    * @param portNumber
    *    The port number to start the http server on. 
    */
   public void startServer(int portNumber)
   {
      try
      {
         httpServer = HttpServer.create(new InetSocketAddress(portNumber), 0);
         //Create a new context for the given context and handler
         httpServer.createContext("/", new HttpHandler()
               {
                  @Override
                  public void handle(HttpExchange t) throws IOException 
                  {  
                     // Create the response data to send back from the request.
                     String response = "<html><body>Stubbed Page Returned</body></html>";
                     // Set the response header status and length
                     t.sendResponseHeaders(200, response.getBytes().length);
                     //Write the response string
                     OutputStream os = t.getResponseBody();
                     os.write(response.getBytes());
                     os.close();
                     
                     // Fire the event to the listeners
                     HTTPRequestEvent event = new HTTPRequestEvent();
                     event.setEndpoint(t.getRequestURI().toString());
                     event.setMethod(t.getRequestMethod());
                     event.setRequestor(t.getRemoteAddress().getAddress().toString());
                     for (Entry<String, List<String>> header : t.getRequestHeaders().entrySet())
                     {
                        event.getHeaders().put(header.getKey(), header.getValue());
                     }
                     fireHTTPEventOccurredListeners(event);
                  }
               });
         //Create a default executor
         httpServer.setExecutor(null);
         
         httpServer.start();
         
         serverStarted = true; 
         this.portNumber = portNumber;
         fireOnServerStartedListeners(portNumber);
         fireHTTPEventOccurredListeners(new HTTPStatusEvent(true));
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
   
   /**
    * Attempts to stop the http server from running. 
    */
   public void stopServer()
   {
      httpServer.stop(0);
      
      serverStarted = false;
      fireOnServerStoppedListeners();
      fireHTTPEventOccurredListeners(new HTTPStatusEvent(false));
   }
   
   /**
    * Adds a state listener to the manager. 
    * @param listener
    *    The listener to add
    * @return
    *    True if the listener was added, false otherwise (duplicate).
    */
   public boolean addHTTPServerStateListener(HTTPServerStateListener listener)
   {
      return this.stateListeners.add(listener);
   }

   /**
    * Removes a state listener from the manager. 
    * @param listener
    *    The listener to remove
    * @return
    *    True if the listener was removed, false otherwise.
    */
   public boolean removeHTTPServerStateListener(HTTPServerStateListener listener)
   {
      return this.stateListeners.remove(listener);
   }
   
   /**
    * Adds an event listener to the manager. 
    * @param listener
    *    The listener to add
    * @return
    *    True if the listener was added, false otherwise (duplicate).
    */
   public boolean addHTTPEventListener(HTTPEventListener listener)
   {
      return this.eventListeners.add(listener);
   }

   /**
    * Removes an event listener from the manager. 
    * @param listener
    *    The listener to remove
    * @return
    *    True if the listener was removed, false otherwise.
    */
   public boolean removeHTTPEventListener(HTTPEventListener listener)
   {
      return this.eventListeners.remove(listener);
   }
   
   /**
    * Fires the onServerStarted event for the state listeners with the
    * port number the server was started with. Note: This will always
    * fire on the EDT.
    * 
    * @param portNumber
    *    The port number the server was started with.
    */
   protected final void fireOnServerStartedListeners(int portNumber)
   {
      Runnable r = new Runnable()
      {         
         @Override
         public void run()
         {
            for (HTTPServerStateListener listener : stateListeners)
            {
               listener.onServerStarted(portNumber);
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
    * Fires the onServerStopped event for the state listeners with the
    * Note: This will always fire on the EDT.
    */
   protected final void fireOnServerStoppedListeners()
   {
      Runnable r = new Runnable()
      {         
         @Override
         public void run()
         {
            for (HTTPServerStateListener listener : stateListeners)
            {
               listener.onServerStopped();
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
    * Fires the httpServerEventOccurred event for the event listeners with the
    * event details for what occurred. Note: This will always fire on the EDT.
    * 
    * @param event
    *    The event to notify the listeners with.
    */
   protected final void fireHTTPEventOccurredListeners(AbstractEventBean event)
   {
      Runnable r = new Runnable()
      {         
         @Override
         public void run()
         {
            for (HTTPEventListener listener : eventListeners)
            {
               listener.httpServerEventOccurred(event);
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
}
