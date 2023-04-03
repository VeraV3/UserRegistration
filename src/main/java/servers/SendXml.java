package servers;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SendXml extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        System.out.println("U serveru sam za ucitavanje xml-a");

        try{
            stream = response.getOutputStream();
            //TODO prepravi ovu putanju
            // File xml = new File("C:\\Users\\Korisnik\\Desktop\\URegistration\\UserRegistration\\src\\main\\java\\servers\\data.xml");
            String separator = File.separator;
            String filePath = getServletContext().getRealPath(separator+"WEB-INF"+ separator +"data.xml");
            File xml = new File(filePath);
            //System.out.println("napravilaaaaaaaaaaaaaa sam fajlic "+xml);
            response.setContentType("text/xml");
            response.addHeader("Content-Disposition","attachment; filename=data.xml" );
            response.setContentLength( (int) xml.length() );
            FileInputStream input = new FileInputStream(xml);
            buf = new BufferedInputStream(input);
            int readBytes = 0;

            while((readBytes = buf.read()) != -1)
                stream.write(readBytes);
                
        } catch (IOException ioe){
                System.out.println("Izuzetak u citanju iz xml fajla sa podacima");
                throw new ServletException(ioe.getMessage());
        } finally {
                if(stream != null)
                    stream.close();
                if(buf != null)
                    buf.close();
        }     
    }
   
         
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException { 
        doGet(request,response);
    } 

}
