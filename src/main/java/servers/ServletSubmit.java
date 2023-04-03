package servers;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServletSubmit extends HttpServlet{

    private static final long serialVersionUID = 1L;

    public ServletSubmit() {}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println("U serveru sam! metod doGet");
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException{
        
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String address = req.getParameter("address");
        String city= req.getParameter("city");
        String country = req.getParameter("country");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        User user = new User(firstName, lastName, address, city, country, email, password);

        //TODO sredi ovu putanju
        //File putanja  = new File("C:\\Users\\Korisnik\\Desktop\\URegistration\\UserRegistration\\src\\main\\java\\servers\\data.xml");
        String separator = File.separator;
       
       String filePath = getServletContext().getRealPath(separator+"WEB-INF"+ separator +"data.xml");
       File putanja = new File(filePath);
        putanja.setReadable(true);
        String message;
        try {
            message = load(user, putanja);
        } catch (Exception e1) { 
            e1.printStackTrace();
            System.out.println("Exception in puting user to file");
            message = "Exception  in putting user to file";
        }
        System.out.println(message);
        req.getRequestDispatcher("/index.html").include(req,res);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

	
	//TODO obrisi ispis odavde na kraju
    static boolean continentExists(Document doc, User user) {
        Node root = doc.getDocumentElement();
        NodeList maybeContinentList = root.getChildNodes();
                for (int i = 0; i < maybeContinentList.getLength(); i++) {
                    Node maybeContinent = maybeContinentList.item(i);
                    if(maybeContinent.getNodeName() == "continent") {
                         NamedNodeMap nm = maybeContinent.getAttributes();
                        for (int j = 0; j < nm.getLength(); j++) {
                             Attr node =  (Attr)nm.item(j);
                            if(node.getNodeValue().equals(user.getContinent())) {
                                return true;               
                            }
                        }
                    }
                }
        return false;
    }
    //TODO obrisi ispis ovde
    static boolean countryExists(Document doc, User user) {
        NodeList countries = doc.getElementsByTagName("country");
        for(int x = 0; x< countries.getLength(); x++) {
            NamedNodeMap nm = countries.item(x).getAttributes();
            for (int j = 0; j < nm.getLength(); j++) {
                Attr node =  (Attr)nm.item(j);
                if(node.getNodeValue().equals(user.getCountry())) {
                return true;	
                }
            }
        }
        return false;
    }
        
        
    
    static boolean isDuplicate(Document doc, User user){
        NodeList addresses = doc.getElementsByTagName("email");
        for(int x = 0; x< addresses.getLength(); x++) {
            if(addresses.item(x).getTextContent().equals(user.getEmail())) { 	
                return true;
            }
        }
        return false;
    }

    static Node findContinent(Document doc, User user, Node root){
        Node continent = null;
        NodeList maybeContinentList = root.getChildNodes();
                for (int i = 0; i < maybeContinentList.getLength(); i++) {
                    Node maybeContinent = maybeContinentList.item(i);
                    if(maybeContinent.getNodeName() == "continent") {
                         NamedNodeMap nm = maybeContinent.getAttributes();
                        for (int j = 0; j < nm.getLength(); j++) {
                            Attr node =  (Attr)nm.item(j);
                            if(node.getNodeValue().equals(user.getContinent())) {
                                return maybeContinent;
                            }
                        }
                    }
                }
        return continent;
    }
    //TODO obrisati ispis
    //TODO dodati beline za strukturiranje xml-a
    static Element newLeaf(Document doc, User user){
        Element newUser = doc.createElement("user");
        Element nfirst_name = doc.createElement("first_name");
        nfirst_name.setTextContent(user.getFirstName());
        Element nlast_name = doc.createElement("last_name");
        nlast_name.setTextContent(user.getLastName());
        Element naddress = doc.createElement("address");
        naddress.setTextContent(user.getAddress());
        Element ncity = doc.createElement("city");
        ncity.setTextContent(user.getCity());
        Element nemail = doc.createElement("email");
        nemail.setTextContent(user.getEmail());
        Element npassword = doc.createElement("password");
        npassword.setTextContent(user.getPassword());
        newUser.appendChild(nfirst_name);
        newUser.appendChild(nlast_name);
        newUser.appendChild(naddress);
        newUser.appendChild(ncity);
        newUser.appendChild(nemail);
        newUser.appendChild(npassword);
        return newUser;    
    }

    static Element newCountryNode(Document doc, User user){
        Element newCountry = doc.createElement("country");
        newCountry.setAttribute("name", user.getCountry());
        Element newUser = newLeaf(doc, user);
        newCountry.appendChild(newUser);
        return newCountry;
    }
  
    static Element newContinentNode(Document doc, User user){
        Element newContinent = doc.createElement("continent"); 
        newContinent.setAttribute("name", user.getContinent());
        Element newCountry = newCountryNode(doc, user);
        newContinent.appendChild(newCountry);
        return newContinent;	
    }
    //TODO optimizacija
    static String load(User user, File path) throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException {
        StringBuffer message = null;
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.parse(path);
        Node root = doc.getDocumentElement();
        if(!isDuplicate(doc, user)) {	
            if(!continentExists(doc, user)) { 
                message = new StringBuffer("Kontinent ne postoji, treba da napravim kontinent");
                Element newContinent = newContinentNode(doc, user);
                root.appendChild(newContinent);                     
            }
            else if(!countryExists(doc, user)) { 
               message = new StringBuffer("Drzava ne postoji, treba da napravim novu drzavu");
               Element newCountry = newCountryNode(doc, user);
               Node cont = findContinent(doc, user, root);
               cont.appendChild(newCountry);
           }else{
                NodeList maybeContinentList = root.getChildNodes();
                for (int i = 0; i < maybeContinentList.getLength(); i++) {
                    Node maybeContinent = maybeContinentList.item(i);
                    if(maybeContinent.getNodeName() == "continent") {
                         NamedNodeMap nm = maybeContinent.getAttributes();
                        for (int j = 0; j < nm.getLength(); j++) {
                             Attr node =  (Attr)nm.item(j);
                            if(node.getNodeValue().equals(user.getContinent())) {
                                 NodeList maybeCountryList = maybeContinent.getChildNodes();
                                for(int k = 0; k<maybeCountryList.getLength(); k++) {
                                     Node maybeCountry = maybeCountryList.item(k);
                                    if(maybeCountry.getNodeName() == "country") {
                                        NamedNodeMap nm2 = maybeCountry.getAttributes();
                                        for (int d = 0; d < nm2.getLength(); d++) {
                                            Attr node2 =  (Attr)nm2.item(d);
                                            if(node2.getNodeValue().equals(user.getCountry())) {
                                                Element newUser = newLeaf(doc, user);
                                                maybeCountry.appendChild(newUser);
                                            }
                                        }
                                    }
                                }
                            }      
                        }
                    }
                }  
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            File xml = path;
            Result output = new StreamResult(xml); 
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
            message = new StringBuffer("User created");
        }
        else{
            message = new StringBuffer("User already exists");
        }
        return message.toString();
    }

}
