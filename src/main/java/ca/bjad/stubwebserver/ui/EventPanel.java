package ca.bjad.stubwebserver.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ca.bjad.stubwebserver.HTTPServerManager;
import ca.bjad.stubwebserver.listeners.HTTPEventListener;
import ca.bjad.stubwebserver.model.AbstractEventBean;

/**
 * Panel showing the table of events that have occurred while the 
 * http server app has been running (including start, stop, and 
 * http requests)
 *
 * @author 
 *   Ben Dougall
 */
public class EventPanel extends JPanel implements HTTPEventListener
{
   private static final long serialVersionUID = -6978247592324190924L;
   
   private JTable eventTable;
   private JScrollPane scrollPane;
   
   private EventTableModel tableModel;
   
   /**
    * Constructor, building the controls in the panel.
    */
   public EventPanel()
   {
      super(new GridLayout(1, 1, 1, 1), true);
      setBorder(new TitledBorder("Events:"));
      createEventAndAddTable();
      
      HTTPServerManager.instance().addHTTPEventListener(this);
      TableColumnResizingHelper.resizeColumnsWithExtraSpaceToSpecificColumn(eventTable, scrollPane, 2);
   }

   /**
    * Creates and configures the event table and adds it 
    * to the panel.
    */
   private void createEventAndAddTable()
   {
      tableModel = new EventTableModel();
      tableModel.addTableModelListener(new TableModelListener()
      {         
         @Override
         public void tableChanged(TableModelEvent e)
         {
            TableColumnResizingHelper.resizeColumnsWithExtraSpaceToSpecificColumn(eventTable, scrollPane, 2);
         }
      });
      
      eventTable = new JTable(tableModel);
      eventTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
      scrollPane = new JScrollPane(eventTable);
      this.add(scrollPane);
   }

   @Override
   public void httpServerEventOccurred(AbstractEventBean eventDetails)
   {
      tableModel.addEvent(eventDetails);
   }
}
