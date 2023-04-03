package servers;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletDelete() {}
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        String email = req.getParameter("email");
        System.out.println("U servletu za brisanje sam  i dobila sam zadatak da obrisem "+ email);
        //TODO sredi putanju
        // File putanja  = new File("C:\\Users\\Korisnik\\Desktop\\URegistration\\UserRegistration\\src\\main\\java\\servers\\data.xml");
       
       String separator = File.separator;
       String filePath = getServletContext().getRealPath(separator+"WEB-INF"+ separator +"data.xml");
       File putanja = new File(filePath);
        //TODO napraviti stranice za greske

        try {
            brisi(putanja, email);
        } catch (Exception e) {
            System.out.println("Izuzetak u brisanju ");
            e.printStackTrace();
        } 

        try {
            req.getRequestDispatcher("index2.html").include(req,res);
        } catch (ServletException e) {
            System.out.println("Izuzetak u redirektu u servletDelete");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static boolean brisi(File path, String email) throws ParserConfigurationException, SAXException, IOException, TransformerException{
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.parse(path);
        NodeList addresses = doc.getElementsByTagName("email");
        for(int x = 0; x< addresses.getLength(); x++) {
            System.out.println("U adresama sam, tekuca je: " + addresses.item(x).getTextContent());
            if(addresses.item(x).getTextContent().equals(email)) { 	
                Node parent = addresses.item(x).getParentNode();
                Node grandparent = parent.getParentNode();
                grandparent.removeChild(parent);
                System.out.println("Trebalo bi da sam ovo obrisala " + parent.getFirstChild().getTextContent());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                File xml = path;
                Result output = new StreamResult(xml); 
                Source input = new DOMSource(doc);
                transformer.transform(input, output);
                return true;
            }
        }
        return false;
    }   
}
