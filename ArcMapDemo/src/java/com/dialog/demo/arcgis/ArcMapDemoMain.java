package com.dialog.demo.arcgis;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.esri.runtime.ArcGISRuntime;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.JMap;

import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;

public class ArcMapDemoMain {

    private JFrame window;
    private JMap map;

    public ArcMapDemoMain() {
        window = new JFrame();
        window.setSize(800, 600);
        window.setLocationRelativeTo(null); // center on screen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new BorderLayout(0, 0));

        // dispose map just before application window is closed.
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                map.dispose();
            }
        });

        
        // If this application is going to be deployed for testing or release
        // please provide a license string to the setLicense method of the
        // ArcGISRuntime class.
        //
        // ArcGISRuntime.setLicense("license string here");
        //ArcGISRuntime.setLicense("runtimebasic,101,rus504177040,none,7RCZ04S3EY6ZXNX8A027");
        ArcGISRuntime.setLicense("runtimestandard,101,rus504177040,none,3M1P0H4EN7E94P7EJ060",
                "runtimespatial,101,rus504177040,none,RP4N7J6DS3H1P2ELJ164");

        map = new JMap();
        window.getContentPane().add(map);

        ArcGISTiledMapServiceLayer tiledLayer = new ArcGISTiledMapServiceLayer(
            "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
        map.getLayers().add(tiledLayer);

        // Create a local dynamic map service from your map package file (.mpk)
        //ArcGISLocalDynamicMapServiceLayer localMSLayer =
        //    new ArcGISLocalDynamicMapServiceLayer("C:\\Your\\Map.mpk");
        //map.getLayers().add(localMSLayer);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
          
            @Override
            public void run() {
                try {
                    ArcMapDemoMain application = new ArcMapDemoMain();
                    application.window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
