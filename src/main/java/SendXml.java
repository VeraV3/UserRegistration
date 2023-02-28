import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendXml extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        //get web.xml for display by a servlet
        String file = "/data.xml";
       
        URL url = null;
        URLConnection urlConn = null;  
        PrintWriter out = null;
        BufferedInputStream buf = null;
       try{
       out = response.getWriter();
       url = getServletContext().getResource(file);
       //set response header
        response.setContentType("text/xml");
      
        urlConn = url.openConnection();
       //establish connection with URL presenting web.xml
       urlConn.connect();
       buf = new BufferedInputStream(urlConn.getInputStream());
       int readBytes = 0;
  
       //read from the file; write to the PrintWriter
       while((readBytes = buf.read()) != -1)
          out.write(readBytes);
  
       } catch (MalformedURLException mue){
      
             throw new ServletException(mue.getMessage());
             
       } catch (IOException ioe){
       
          throw new ServletException(ioe.getMessage());
           
       } finally {
       
  //close the input/output streams
       if(out != null)
           out.close();
        if(buf != null)
            buf.close();
            }
      
      }
         
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        doGet(request,response);
    } 
      
    //   <?xml version="1.0" encoding="ISO-8859-1"?>
      
    //   <!DOCTYPE web-app
    //       PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN"
    //       "http://java.sun.com/j2ee/dtds/web-app_2_2.dtd">
      
    //   <web-app>
    //       <servlet><servlet-name>MyServletName</servlet-name>
    //                <servlet-class>MyServlet</servlet-class>
      
                   
    //       </servlet>
          
    //       <servlet-mapping><servlet-name>MyServletName</servlet-name>
    //           <url-pattern>/index.html</url-pattern>
    //       </servlet-mapping>
    //   </web-app>
}
