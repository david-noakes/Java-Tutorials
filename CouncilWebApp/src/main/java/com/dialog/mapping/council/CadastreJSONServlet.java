package com.dialog.mapping.council;
import java.io.*;
import java.math.BigDecimal;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dialog.utilities.GeoPoint;
import com.google.common.io.Files;
import com.google.common.base.Charsets;

@WebServlet("/Cadastre/*")
public class CadastreJSONServlet  extends  HttpServlet{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        if (json == null || json.trim().length() ==0) {
            json=request.getQueryString();
        }
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");
        String jsonOut = ""; 
        PrintWriter out = response.getWriter();
        String reqURI = request.getRequestURI();
        // reqURI will begin with /CouncilWebApp/Data
        // eg /helloworld/Data/test
        if (reqURI.equalsIgnoreCase("/CouncilWebApp/Cadastre/GetParcel")) {
            jsonOut = getCadastreParcel(json, lat, lng);
            out.println(jsonOut);
        } else {
            jsonOut = testJson();
            out.println(jsonOut);
        }
    }
    
    private String testJson(){
        String json = "";
        ServletContext servletContext = getServletContext();
        String contextPath = servletContext.getRealPath(File.separator);

        String collFileName = contextPath + "resources\\geospacial\\Cadastre.Get.json";
        File collFile = new File(collFileName);
        try {
            json = Files.toString(collFile, Charsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{ " + e.toString()+ " }";
        }
        return json;
    }
    
    private String getCadastreParcel(String jsonRequest, String lat, String lng) {
        String jsonOutStr = "";
        if (jsonRequest != null || jsonRequest.trim().length() > 0) {
            // jsonRequest => lat=-33.789814&lng=150.90832699999999&proximity=
            // 1 degree ~ 100km == 100000 metres
            // 0.1                = 10000
            // 0.01                = 1000
            // 0.001                = 100
            // 0.0001                = 10
            // 0.00001                = 1
            // 0.000001               = 0.1
            // 0.0000001              = 0.01 (1 cm)  // 7
            // 0.00000001             = 0.001 (1mm)
            // 0.000000001            = 0.0001
            // 0.0000000001           = 0.00001
            // 0.00000000001          = 0.000001 (micro meter) //11
            // make a box +- 10m on centroid
            BigDecimal latCentre = new BigDecimal(lat);
            BigDecimal lngCentre = new BigDecimal(lng);
            BigDecimal tenMetres = new BigDecimal(0.0001);
            GeoPoint topLeft     = new GeoPoint(lngCentre.subtract(tenMetres), latCentre.subtract(tenMetres)); 
            GeoPoint topRight    = new GeoPoint(lngCentre.add(tenMetres), latCentre.subtract(tenMetres)); 
            GeoPoint bottomLeft  = new GeoPoint(lngCentre.subtract(tenMetres), latCentre.add(tenMetres)); 
            GeoPoint bottomRight = new GeoPoint(lngCentre.add(tenMetres), latCentre.add(tenMetres)); 
            String s1 = bottomLeft.toJson().toJSONString();
            String s2 = bottomLeft.toJson().toString();
            
            JSONObject jsonOut = new JSONObject();
            JSONObject jParcel = new JSONObject();
            jsonOut.put("parcel",jParcel);
            jsonOut.put("Lat", latCentre);
            jsonOut.put("Long", lngCentre);
            jParcel.put("Id",2969644);
            jParcel.put("Address",null);
            jParcel.put("LotNumber",13);
            jParcel.put("LotPlan","LP262885");
            jParcel.put("LotSection","");
            jParcel.put("Lga","Data Not Currently Available");
            JSONArray jLandUse = new JSONArray();  // this one is empty
            jParcel.put("LandUseZones",jLandUse);
            jParcel.put("LEP","Data Not Currently Available");
            jParcel.put("LegislationLink","");
            jParcel.put("LegislationReferenceLink","");
            jParcel.put("BuildingHeight","Data Not Currently Available");
            jParcel.put("FloorSpaceRatio","Data Not Currently Available");
            jParcel.put("MapTiles","N/A");
            JSONObject jGeometry = new JSONObject();
            jParcel.put("Geometry", jGeometry);
            jGeometry.put("isValid", true);
            jGeometry.put("type", "Polygon");
            JSONArray jPolygons = new JSONArray();
            jGeometry.put("coordinates", jPolygons);
            JSONArray jPolygon = new JSONArray();  // this is a nested array
            JSONArray jPolyPoint = new JSONArray(); 
            jPolyPoint.add(topLeft.getX());
            jPolyPoint.add(topLeft.getY());  // yes: Lng , Lat pairs
            jPolygon.add(jPolyPoint);
            jPolyPoint = new JSONArray(); 
            jPolyPoint.add(topLeft.getX());
            jPolyPoint.add(topLeft.getY());  // yes: Lng , Lat pairs
            jPolygon.add(jPolyPoint);
            jPolyPoint = new JSONArray(); 
            jPolyPoint.add(topLeft.getX());
            jPolyPoint.add(topLeft.getY());  // yes: Lng , Lat pairs
            jPolygon.add(jPolyPoint);
            jPolyPoint = new JSONArray(); 
            jPolyPoint.add(topLeft.getX());
            jPolyPoint.add(topLeft.getY());  // yes: Lng , Lat pairs
            jPolygon.add(jPolyPoint);
            jPolyPoint = new JSONArray(); 
            jPolyPoint.add(topLeft.getX());
            jPolyPoint.add(topLeft.getY());  // closure: first == last
            jPolygon.add(jPolyPoint);
            jPolygons.add(jPolygon);
            JSONObject jExts = new JSONObject();
            jParcel.put("extents", jExts);
            jExts.put("Top", topLeft.getY()); 
            jExts.put("Left", topLeft.getX());
            jExts.put("Right", bottomRight.getX());
            jExts.put("Bottom", bottomRight.getY());
            JSONObject jCentre = new JSONObject();
            jParcel.put("centroid", jCentre);
            jCentre.put("X", lngCentre);
            jCentre.put("Y", latCentre);
            jParcel.put("BufferDistance",0);
            jParcel.put("BufferedGeometry",null);
            jParcel.put("ExecutionTime", 418.9836);
            JSONArray jLayers = new JSONArray();
            jParcel.put("IntersectingLayers",jLayers);
            jParcel.put("BufferedIntersectingLayers",null);
            jsonOutStr = jsonOut.toString();
        }
        
        return jsonOutStr;
    }
}
