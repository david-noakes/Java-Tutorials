package hello;
import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet("/Data/*")
public class JSONServlet extends  HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        String reqURI = request.getRequestURI();
        // reqURI will begin with /helloworld/Data
        // eg /helloworld/Data/test
        
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
        PrintWriter out = response.getWriter();
        out.println(jHead);
//        for(int i=0;i<arrayObj.size();i++){
//            out.println(arrayObj.get(i).toString());
//        }
    }
}
