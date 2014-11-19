package com.dialog.demo.arcgis;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
import com.esri.runtime.ArcGISRuntime;


//Copyright 2011-2013 ESRI
//
//All rights reserved under the copyright laws of the United States
//and applicable international laws, treaties, and conventions.
//
//You may freely redistribute and use this sample code, with or
//without modification, provided you include the original copyright
//notice and use restrictions.
//
//See the use restrictions.
//

public class ArcGisQueryDemo {

// resources Database is served via http
final String URL_STATES = "http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/Demographics/ESRI_Census_USA/MapServer/5";

// query attributes
final String[] ATTR_NAMES  =
   new String[] {"STATE_NAME", "SUB_REGION", "STATE_FIPS", "STATE_ABBR", "POP2000", "POP2007"};
final String[] ATTR_HEADER =
   new String[] {"State Name", "Region", "FIPS", "Abbrevation", "Population in 2000", "Population in 2007"};

// ------------------------------------------------------------------------
// Constructors
// ------------------------------------------------------------------------
public ArcGisQueryDemo() {
 ArcGISRuntime.initialize();
}

// ------------------------------------------------------------------------
// Core functionality
// ------------------------------------------------------------------------
/**
* Executes a query.
* @param txtQuery Input field that has the State name. The query will get all States
* whose name matches this.
* @param tblModelQueryResult Query result will be populated in this.
*/
private void executeQuery(JTextField txtQuery, final DefaultTableModel tblModelQueryResult) {

 // return if state name is not valid.
 String stateName = txtQuery.getText();
 if (stateName == null || stateName.isEmpty()) {
   return;
 }

 // initialize the result - add the column headers if not already present,
 // and clear existing rows.
 if (tblModelQueryResult.getColumnCount() == 0) {
   for (String attrHeader : ATTR_HEADER) {
     tblModelQueryResult.addColumn(attrHeader);
   }
 }
 tblModelQueryResult.setRowCount(0);

 // create a query to get attributes of states with matching name.
 QueryParameters query = new QueryParameters();
 // specify the attributes to be fetched.
 query.setOutFields(ATTR_NAMES);
 // specify the query criteria.
 query.setWhere("STATE_NAME LIKE '%" + stateName + "%'");

 // execute the query.
 QueryTask task = new QueryTask(URL_STATES);

 task.executeAsync(query, new CallbackListener<FeatureResult>(){

   @Override
   public void onError(Throwable e){
     e.printStackTrace();
   }

   @Override
   public void onCallback(FeatureResult results) {
     // if there are no matching records, then return.
     if (results.featureCount() < 1){
       Object[] rowData = new Object[ATTR_NAMES.length];
       rowData[0] = "< No records found! >";
       tblModelQueryResult.addRow(rowData);
       return;
     }

     System.out.println("results: " +results.featureCount());
     // add attributes of all states obtained after executing the query.
     Iterator<Object> it = results.iterator();
     while (it.hasNext()) {
       Object o = it.next();
       if (o instanceof Feature) {
         // get attributes' value in the same order as the header.
         Object[] rowData = new Object[ATTR_NAMES.length];
         int index = 0;
         for (String attrName : ATTR_NAMES){
           rowData[index++] = ((Feature) o).getAttributeValue(attrName);
         }
         // add one row per state
         tblModelQueryResult.addRow(rowData);
       }
     }
   }
 });
}

// ------------------------------------------------------------------------
// Static methods
// ------------------------------------------------------------------------
/**
* Starting point of this application.
* @param args arguments to this application.
*/
public static void main(String[] args) {
 SwingUtilities.invokeLater(new Runnable() {
   @Override
   public void run() {
     try {
       // instance of this application
         ArcGisQueryDemo queryApp = new ArcGisQueryDemo();

       // create the UI, including the map, for the application.
       JFrame appWindow = queryApp.createWindow();
       appWindow.add(queryApp.createUI());
       appWindow.setVisible(true);
     } catch (Exception e) {
       // on any error, display the stack trace.
       e.printStackTrace();
     }
   }
 });
}

// ------------------------------------------------------------------------
// Public methods
// ------------------------------------------------------------------------


/**
* Creates and displays the UI, including the map, for this application.
*/
public JComponent createUI() throws Exception {
 // application content
 JComponent contentPane = createContentPane();

 // application title
 JTextArea lblTitle = new JTextArea("Query without a map");
 lblTitle.setEditable(false);
 lblTitle.setForeground(Color.BLACK);
 lblTitle.setBackground(null);
 lblTitle.setMaximumSize(new Dimension(350, 20));

 // query label
 JTextArea lblQuery = new JTextArea("US State name contains (query is case-sensitive): ");
 lblQuery.setEditable(false);
 lblQuery.setForeground(Color.BLACK);
 lblQuery.setBackground(null);
 lblQuery.setMaximumSize(new Dimension(350, 20));

 // query input field
 final JTextField txtQuery = new JTextField();
 txtQuery.setText("New");
 txtQuery.setMaximumSize(new Dimension(350, 20));

 // query result - scrollable table
 final DefaultTableModel tblModelQueryResult = new DefaultTableModel();
 JTable tablQueryResult = new JTable(tblModelQueryResult);
 JScrollPane tblQueryResultScrollable = new JScrollPane(tablQueryResult);

 // query button
 JButton btnQuery = new JButton("Query");
 btnQuery.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
     // execute query when button is pressed.
     executeQuery(txtQuery, tblModelQueryResult);
   }
 });
 btnQuery.setMaximumSize(new Dimension(150, 20));
 btnQuery.setAlignmentX(Component.CENTER_ALIGNMENT);

 // group the above UI items into a panel
 final JPanel controlPanel = new JPanel();
 BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
 controlPanel.setLayout(boxLayout);
 controlPanel.setBorder(new LineBorder(new Color(0, 0, 0, 100), 5, false));

 controlPanel.add(lblTitle);
 controlPanel.add(lblQuery);
 controlPanel.add(txtQuery);
 controlPanel.add(btnQuery);
 controlPanel.add(tblQueryResultScrollable);

 // add the panel to the main window
 contentPane.add(controlPanel);

 return contentPane;
}

// ------------------------------------------------------------------------
// Private methods
// ------------------------------------------------------------------------
/**
* Creates a window.
* @return a window.
*/
private JFrame createWindow() {
 JFrame window = new JFrame("Query Application");
 window.setBounds(100, 100, 1000, 700);
 window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 window.getContentPane().setLayout(new BorderLayout(0, 0));
 return window;
}

/**
* Creates a content pane.
* @return a content pane.
*/
private static JLayeredPane createContentPane() {
 JLayeredPane contentPane = new JLayeredPane();
 contentPane.setBounds(100, 100, 1000, 700);
 contentPane.setLayout(new BorderLayout(0, 0));
 contentPane.setVisible(true);
 return contentPane;
}
}
