package com.dialog.demo.arcgis;


//Copyright 2013 ESRI
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.esri.core.gdb.GdbFeatureTable;
import com.esri.core.gdb.Geodatabase;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.runtime.ArcGISRuntime;
import com.esri.arcgis.datasourcesGDB;

/**
* This sample demonstrates retrieval and display of data independent of any mapping. 
* A Geodatabase object is create from a local geodatabase (.geodatabase file) and 
* a GdbFeatureTable is created from the Geodatabase using the desired layer ID via
* <code>getGdbFeatureTableByLayerId(LAYER_ID)</code>. Queries are then performed 
* against the GdbFeatureTable.
* <p>
* Note: you must have write access to the directory containing the geodatabase 
* in order for the Geodatabase object to be created, and thus for this sample to 
* run properly. By default it is in a subdirectory of the SDK installation directory.
*/
public class ArcGisLocalQueryDemo {

// resources
private static String FSP = System.getProperty("file.separator");
private static final String GDB_PATH = 
   "D:" + FSP + "Downloads" + FSP + "Cadastre_Data_overlays" + FSP + "QLD_DCDB_WOS_CUR.gdb";
//       getPathSampleData() + "beta" + FSP + "geodatabase" + FSP + "DamageInspection6.geodatabase"; 
private static final int LAYER_ID = 0;

// query attributes
private final String[] ATTR_NAMES  =
   new String[] {"OBJECTID", "PLACENAME", "FIRSTNAME", "LASTNAME", "TYPDAMAGE", "PRIMCAUSE"};
private final String[] ATTR_HEADER =
   new String[] {"Object ID", "Place", "First Name", "Last Name", "Damage type", "Cause"};
private JLayeredPane contentPane;
private GdbFeatureTable table;

// ------------------------------------------------------------------------
// Constructor
// ------------------------------------------------------------------------
public ArcGisLocalQueryDemo() {
 ArcGISRuntime.initialize();
}

// ------------------------------------------------------------------------
// Core functionality
// ------------------------------------------------------------------------
/**
* Executes a query.
* @param txtQuery Input query.
* @param tblModelQueryResult Query result will be populated in this.
*/
private void executeQueryInBatch(JTextField txtQuery, final DefaultTableModel tblModelQueryResult) {

 // return if input is not valid or table null
 String damageType = txtQuery.getText();
 if (table == null || damageType == null || damageType.isEmpty()) {
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

 // create a query 
 QueryParameters query = new QueryParameters();
 // specify the attributes to be fetched.
 query.setOutFields(ATTR_NAMES);
 //query.setReturnIdsOnly(true);
 // specify the query criteria.
 query.setWhere("TYPDAMAGE LIKE '%" + damageType + "%'");

 // execute the query.
 // this option returns result as an iterator.
 // use this option when fitting all features in memory may not be practical. 
 table.queryFeatures(query, new CallbackListener<FeatureResult>() {

   @Override
   public void onError(Throwable e) {
     JOptionPane.showMessageDialog(contentPane, "Error: "+e.getLocalizedMessage(), "", JOptionPane.ERROR_MESSAGE);
   }

   @Override
   public void onCallback(FeatureResult objs) {
     for (Object objFeature : objs) {
       Feature feature = (Feature) objFeature;

       // get attributes' value in the same order as the header.
       Object[] rowData = new Object[ATTR_NAMES.length];
       int index = 0;
       for (String attrName : ATTR_NAMES) {
         rowData[index++] = feature.getAttributeValue(attrName);
       }
       // add one row per feature
       tblModelQueryResult.addRow(rowData);
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
         ArcGisLocalQueryDemo LocalQueryApp = new ArcGisLocalQueryDemo();

       // create the UI, including the map, for the application.
       JFrame appWindow = LocalQueryApp.createWindow();
       appWindow.add(LocalQueryApp.createUI());
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
 contentPane = createContentPane();

 // description
 JPanel txtDescription = createDescription();
 txtDescription.setMaximumSize(new Dimension(600, 40));

 // query label
 JTextArea lblQuery = new JTextArea("Damage type contains (query is case-sensitive): ");
 lblQuery.append("(Values: Affected, Destroyed, Inaccessible,  Major, Minor)");
 lblQuery.setEditable(false);
 lblQuery.setForeground(Color.BLACK);
 lblQuery.setBackground(null);
 lblQuery.setMaximumSize(new Dimension(600, 20));

 // query input field
 final JTextField txtQuery = new JTextField();
 txtQuery.setText("Minor");
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
     executeQueryInBatch(txtQuery, tblModelQueryResult);
   }
 });
 btnQuery.setMaximumSize(new Dimension(150, 20));
 btnQuery.setAlignmentX(Component.CENTER_ALIGNMENT);

 // group the above UI items into a panel
 final JPanel controlPanel = new JPanel();
 BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
 controlPanel.setLayout(boxLayout);
 controlPanel.setBorder(new LineBorder(new Color(0, 0, 0, 100), 5, false));

 controlPanel.add(txtDescription);
 controlPanel.add(lblQuery);
 controlPanel.add(txtQuery);
 controlPanel.add(btnQuery);
 controlPanel.add(tblQueryResultScrollable);

 // add the panel to the main window
 contentPane.add(controlPanel);
 
 // create the geodatabase and gdb feature table once
 try {
     String zx =  getPathSampleData() + "beta" + FSP + "geodatabase" + FSP + "DamageInspection6.geodatabase";
     Geodatabase gdb = new Geodatabase(GDB_PATH);
   table = gdb.getGdbFeatureTableByLayerId(LAYER_ID);
 } catch (Exception e) {
   JOptionPane.showMessageDialog(contentPane, "Error: " + e.getLocalizedMessage() + "\r\nSee notes for this application.");
 }

 return contentPane;
}

// ------------------------------------------------------------------------
// Private methods
// ------------------------------------------------------------------------
/**
* Creates a description for this application.
* @return description
*/
private JPanel createDescription() {
 JPanel descriptionContainer = new JPanel();
 descriptionContainer.setLayout(new BoxLayout(descriptionContainer, 0));
 descriptionContainer.setSize(0, 40);
 JTextArea description = new JTextArea(
     "This sample demonstrates retrieval and display of data independent of any mapping. " +
     "Note: you must have write access to the directory containing the geodatabase " +
     "in order for the Geodatabase object to be created, and thus for this sample to " + 
     "run properly. By default it is in a subdirectory of the SDK installation directory.");
 description.setFont(new Font("Verdana", Font.PLAIN, 11));
 description.setForeground(Color.WHITE);
 description.setBackground(new Color(0, 0, 0, 180));
 description.setEditable(false);
 description.setLineWrap(true);
 description.setWrapStyleWord(true);
 description.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
 descriptionContainer.add(description);
 descriptionContainer.setBackground(new Color(0, 0, 0, 0));
 descriptionContainer.setBorder(new LineBorder(Color.BLACK, 3, false));
 return descriptionContainer;
}

/**
* Creates a window.
* @return a window.
*/
private JFrame createWindow() {
 JFrame window = new JFrame("Local Query Application");
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

private static String getPathSampleData() {
 String dataPath = null;
 String javaPath = ArcGISRuntime.getInstallDirectory();
 if (javaPath != null) {
   if (!(javaPath.endsWith("/") || javaPath.endsWith("\\"))){
     javaPath += FSP;
   }
   dataPath = javaPath + "sdk" + FSP + "samples" + FSP + "data" + FSP;
 }
 File dataFile = new File(dataPath);
 if (!dataFile.exists()) { 
   dataPath = ".." + FSP + "data" + FSP;
 }
 return dataPath;
}
}
