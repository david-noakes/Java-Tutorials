package com.dialog.mapping.council;

import java.io.*;
//import java.nio.charset.Charset;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.io.Files;
import com.google.common.base.Charsets;

@WebServlet("/Data/*")
public class DataLayerJSONServlet extends  HttpServlet{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        JSONObject jHead;
        if(br != null){
            json = br.readLine();
        }
        PrintWriter out = response.getWriter();
        String reqURI = request.getRequestURI();
        // reqURI will begin with /CouncilWebApp/Data
        // eg /helloworld/Data/test
        if (reqURI.equalsIgnoreCase("/CouncilWebApp/Data/ListLayers")) {
            json = dataListLayers();
            out.println(json);
        } else {
            jHead = testJson();
            out.println(jHead);
        }
    }
    
    private JSONObject testJson() {
        JSONObject jHead = new JSONObject();
        JSONArray arrayObj=new JSONArray();
        jHead.put("json", arrayObj);
        JSONObject jObj = new JSONObject();
        jObj.put("club", "MCA");
        arrayObj.add(jObj);
        jObj = new JSONObject();
        jObj.put("name", "Fred Nurk");
        arrayObj.add(jObj);
        jObj = new JSONObject();
        jObj.put("d.o.b.", "19-12-1986");
        arrayObj.add(jObj);
        jObj = new JSONObject();
        jObj.put("age", 24);
        arrayObj.add(jObj);
        jObj = new JSONObject();
        jObj.put("Scored", new Double(66.67));
        arrayObj.add(jObj);

        return jHead;
    }
    
    private String dataListLayers() {
        String json = "";
        //String userDir = System.getProperty("user.dir");
        //String userDir = System.getProperty("cataline.home");
        ServletContext servletContext = getServletContext();
        String contextPath = servletContext.getRealPath(File.separator);

        String collFileName = contextPath + "resources\\geospacial\\Data.Layers.json";
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
}
