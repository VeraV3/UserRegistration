package servers;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import javax.servlet.ServletException;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

public class SendXml extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          //get the file name from the 'file' parameter
      //String fileName = (String) request.getParameter("file");
      //if (fileName == null || fileName.equals(""))
         //  throw new ServletException(
           // "Invalid or non-existent file parameter in SendXml servlet.");
      
      // add the .doc suffix if it doesn't already exist
      //if (fileName.indexOf(".xml") == -1)
        //  fileName = fileName + ".xml";
          
      //where are Word files kept? xml-dir returns a content of a directory as a xml
     //String xmlDir = getServletContext().getInitParameter("xml-dir");
     //if (xmlDir == null || xmlDir.equals(""))
       //    throw new ServletException(
         //    "Invalid or non-existent xmlDir context-param.");
          
      ServletOutputStream stream = null;
      BufferedInputStream buf = null;
     System.out.println("U serveru sam za ucitavanje xml-a");
     try{
     
     stream = response.getOutputStream();
     //TODO prepravi ovu putanju
     File xml = new File("C:\\Users\\Korisnik\\Desktop\\URegistration\\UserRegistration\\src\\main\\java\\servers\\data.xml");
     
      //set response headers
      response.setContentType("text/xml");
      
      response.addHeader(
        "Content-Disposition","attachment; filename=data.xml" );

      response.setContentLength( (int) xml.length() );
      
     FileInputStream input = new FileInputStream(xml);
     buf = new BufferedInputStream(input);
     int readBytes = 0;

     //read from the file; write to the ServletOutputStream
     while((readBytes = buf.read()) != -1)
        stream.write(readBytes);

     } catch (IOException ioe){
     
        throw new ServletException(ioe.getMessage());
         
     } finally {
     
//close the input/output streams
     if(stream != null)
         stream.close();
      if(buf != null)
          buf.close();
          }
    
        /* 
        response.setContentType("application/xml");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Request-Method", "GET");
       
        OutputStream os = response.getOutputStream();

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            FileReader fileReader = new FileReader("./data.xml");
            StreamSource source = new StreamSource(fileReader);
            StreamResult result = new StreamResult(os);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            System.out.println("Ne radi transformer transform");
            e.printStackTrace();
        } catch (TransformerException e) {
            System.out.println("Ne  moze da se napravi transformer");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        os.flush();

        */
//         //get web.xml for display by a servlet
//         String file = "/data.xml";
       
//         URL url = null;
//         URLConnection urlConn = null;  
//         PrintWriter out = null;
//         BufferedInputStream buf = null;
//        try{
//        out = response.getWriter();
//        url = getServletContext().getResource(file);
//        //set response header
//         response.setContentType("text/xml");
      
//         urlConn = url.openConnection();
//        //establish connection with URL presenting web.xml
//        urlConn.connect();
//        buf = new BufferedInputStream(urlConn.getInputStream());
//        int readBytes = 0;
  
//        //read from the file; write to the PrintWriter
//        while((readBytes = buf.read()) != -1)
//           out.write(readBytes);
  
//        } catch (MalformedURLException mue){
      
//              throw new ServletException(mue.getMessage());
             
//        } catch (IOException ioe){
       
//           throw new ServletException(ioe.getMessage());
           
//        } finally {
       
//   //close the input/output streams
//        if(out != null)
//            out.close();
//         if(buf != null)
//             buf.close();
//             }
      
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
