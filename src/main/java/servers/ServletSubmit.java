package servers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        //PrintWriter pw = res.getWriter();
        //pw.println("<h2>uspesno je dodat korisnik</h2>");
        System.out.println("u serveru sam, u metodu service");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String address = req.getParameter("address");
        String city= req.getParameter("city");
        String country = req.getParameter("country");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        User user = new User(firstName, lastName, address, city, country, email, password);

        //TODO sredi ovu putanju
        File putanja  = new File("C:\\Users\\Korisnik\\Desktop\\pr2\\UserRegistration\\src\\main\\java\\servers\\data.xml");
        // String putanja1 = putanja.getAbsolutePath();
        putanja.setReadable(true);

        try {
            load(user, putanja);
        } catch (Exception e1) { 
            e1.printStackTrace();
            System.out.println("Exception in puting user to file");
        }

       // req.getRequestDispatcher("/page1.html").include(req,res);
        //System.out.println(req.getParameterValues(getServletInfo()));
        //System.out.println( req.toString());

    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

	
	//TODO obrisi ispis odavde na kraju
    static boolean continentExists(Document doc, User user) {
        NodeList adrese = doc.getElementsByTagName("continent");
        for(int x = 0; x< adrese.getLength(); x++) {
            if(adrese.item(x).getTextContent().equals(user.getContinent())) {
                System.out.println("Continent exists!");
                return true;	
            }
        }
        System.out.println("Continent doesnt exist!");
        return false;
    }
    //TODO obrisi ispis ovde
    static boolean countryExists(Document doc, User user) {
        NodeList adrese = doc.getElementsByTagName("country");
        for(int x = 0; x< adrese.getLength(); x++) {
            if(adrese.item(x).getTextContent().equals(user.getCountry())) {
                System.out.println("drzava vec postoji");
                return true;	
            }
        }
        System.out.println("Drzava postoji");
        return false;
    }
    
    static boolean check(Document doc, User user){
        NodeList addresses = doc.getElementsByTagName("email");
        for(int x = 0; x< addresses.getLength(); x++) {
            if(addresses.item(x).getTextContent().equals(user.getEmail())) {
                return true;	
            }
        }
        return false;
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
        System.out.println("Novii korisnik je napravljen");
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
    static void load(User user, File path) throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.parse(path);
        Node root = doc.getDocumentElement();
        boolean indikatorDuplikata = check(doc, user) ;
        if(indikatorDuplikata==false) {	
            NodeList maybeContinentList = root.getChildNodes();
            for (int i = 0; i < maybeContinentList.getLength(); i++) {
                // System.out.println("1. nivo	" + i);
                Node maybeContinent = maybeContinentList.item(i);
                if(maybeContinent.getNodeName() == "continent") {
                    NamedNodeMap nm = maybeContinent.getAttributes();
                    for (int j = 0; j < nm.getLength(); j++) {
                        //System.out.println("Prolazim kroz atribute cvora " + i + " i redni broj atributa je " + j);
                        Attr node =  (Attr)nm.item(j);
                        if(node.getNodeValue().equals(user.getContinent())) {
                            NodeList maybeCountryList = maybeContinent.getChildNodes();
                            for(int k = 0; k<maybeCountryList.getLength(); k++) {
                                //System.out.println("	2. nivo		" + k);
                                Node maybeCountry = maybeCountryList.item(k);
                                if(maybeCountry.getNodeName() == "country") {
                                    NamedNodeMap nm2 = maybeCountry.getAttributes();
                                    for (int d = 0; d < nm2.getLength(); d++) {
                                        //	System.out.println("		3. nivo		" + d);
                                        Attr node2 =  (Attr)nm2.item(d);
                                        if(node2.getNodeValue().equals(user.getCountry())) {
                                            Element newUser = newLeaf(doc, user);
                                            maybeCountry.appendChild(newUser);
                                            
                                        }
                                    }
                                }
                            }
                            if(!countryExists(doc, user) && continentExists(doc, user)  ) { //!check(doc, user) &&  u uslovu
                                 System.out.println("Drzava ne postoji, kontinent postoji, treba da napravim drzavu");
                                Element newCountry = newCountryNode(doc, user);
                                maybeContinent.appendChild(newCountry);
                            }
                                
                        }
                                  
                    }
                }
             
            }
            if(!continentExists(doc, user)) { // && !check(doc, user) u uslovu
                System.out.println("Kontinent ne postoji, treba da napravim kontinent");
                Element newContinent = newContinentNode(doc, user);
                root.appendChild(newContinent);
                                    
            }
        }
        else{
            System.out.println("User already exists!");
           
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        File xml = path;
        Result output = new StreamResult(xml); 
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }

}
