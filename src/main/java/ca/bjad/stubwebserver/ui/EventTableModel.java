package ca.bjad.stubwebserver.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ca.bjad.stubwebserver.model.AbstractEventBean;

/**
 * Table model for the event table, defining how to display the 
 * data to the user. 
 *
 * @author 
 *   Ben Dougall
 */
public class EventTableModel extends DefaultTableModel
{
   private static final long serialVersionUID = -4749197468840902762L;
   
   /**
    * The date formatter for the timestamp column
    */
   protected static final DateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("hh:mm:ss");
   
   /**
    * The list of directories to display in the table. 
    */
   protected List<AbstractEventBean> events = new LinkedList<AbstractEventBean>();
   
   /**
    * The column names to show in the table.
    */
   private static final String[] COLUMN_NAMES = new String[] 
         {
               "Time",
               "Type",
               "Details",
         };
   
   
   @Override 
   public int getColumnCount()
   {
      return COLUMN_NAMES.length;
   }
   
   @Override
   public String getColumnName(int index)
   {
      return COLUMN_NAMES[index];
   }
   
   @Override
   public Class<?> getColumnClass(int index)
   {
      return String.class;
   }
   
   @Override
   public int getRowCount()
   {
      return getEvents().size();
   }
   
   @Override
   public boolean isCellEditable(int row, int column)
   {
      return false;
   }
   
   @Override
   public Object getValueAt(int row, int column)
   {
      AbstractEventBean event = getEvents().get(row);
      String returnData = "";
      switch (column)
      {
      case 0:
         returnData = TIMESTAMP_FORMATTER.format(event.getTimestamp());
         break;
      case 1:
         returnData = event.getEventType();
         break;
      case 2:
         returnData = event.getDescriptionText();
         break;
      }
      return returnData + " ";
   }

   /**
    * Returns the value of the DirectoryTableModel instance's 
    * directories property.
    *
    * @return 
    *   The value of directories
    */
   public List<AbstractEventBean> getEvents()
   {
      if (this.events == null)
      {
         this.events = new LinkedList<>();
      }
      return this.events;
   }
   
   /**
    * Adds an event to the model so it can be displayed to the user 
    * in the event table.
    * @param event
    *    The event to add to the table.
    */
   public void addEvent(AbstractEventBean event)
   {
      this.getEvents().add(event);
      fireTableDataChanged();
   }
}
