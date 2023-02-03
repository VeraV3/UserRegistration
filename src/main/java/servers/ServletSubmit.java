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

    public ServletSubmit() {
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response){
        System.out.println("U serveru sam!");
        
    }

    public void service(ServletRequest req, ServletResponse res)
    throws ServletException, IOException
{
    res.setContentType("text/html");
    PrintWriter pw = res.getWriter();
    pw.println("<h2>uspesno je dodat korisnik</h2>");

    System.out.println("in service");
    //response.setContentType("text/html"); // Setting the content type to text
   // PrintWriter out = response.getWriter();

    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String address = req.getParameter("address");
    String city= req.getParameter("city");
    String country = req.getParameter("country");
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    
   User user = new User(firstName, lastName, address, city, country, email, password);
   //pw.print("Welcome " + user.toString() ); 
    
    // Printing the username
    
    //System.out.println(user.toString());
    //String adresa = System. getProperty("user. dir");
    //TODO sredi ovu putanju
    File putanja  = new File("C:\\Users\\Korisnik\\Desktop\\pr2\\UserRegistration\\src\\main\\java\\servers\\data.xml");
    
    putanja.setReadable(true);boolean mozeDaSeCita = putanja.canRead();
   // String putanja1 = putanja.getAbsolutePath();
    try {
        ucitaj(user, putanja);

    } catch (Exception e1) {
        
        
        e1.printStackTrace();
        System.out.println("IZUZETAK U UCITAVANJU kORISNIKA U FAJL");
        System.out.println( "adresa xml fajla je " + putanja + "i moze li da se cita "+ mozeDaSeCita );
    }
    
                //pv2.write(user.toString());
    //req.getRequestDispatcher("/page1.html").include(req,res);
    //System.out.println(req.getParameterValues(getServletInfo()));
    //System.out.println( req.toString());


    // Printing the username 

   req.getRequestDispatcher("/page1.html").include(req,res);
//	System.out.println(req.getParameterValues(getServletInfo()));
    //System.out.println( req.toString());
}
/* 
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
}
*/

	
	  
static boolean continentExists(Document doc, User user) {
    NodeList adrese = doc.getElementsByTagName("continent");
    for(int x = 0; x< adrese.getLength(); x++) {
        //System.out.println(adrese.item(x).getTextContent());
        if(adrese.item(x).getTextContent().equals(user.getContinent())) {
            System.out.println("kontinent vec postoji!!!");
            return true;	
        }
    }
    return false;
}

static boolean countryExists(Document doc, User user) {
    NodeList adrese = doc.getElementsByTagName("country");
    for(int x = 0; x< adrese.getLength(); x++) {
        //System.out.println(adrese.item(x).getTextContent());
        if(adrese.item(x).getTextContent().equals(user.getCountry())) {
            System.out.println("drzava vec postoji");
            return true;	
        }
    }
    return false;
}
    
static boolean check(Document doc, User user)
    {
        NodeList adrese = doc.getElementsByTagName("email");
        for(int x = 0; x< adrese.getLength(); x++) {
            //System.out.println(adrese.item(x).getTextContent());
            if(adrese.item(x).getTextContent().equals(user.getEmail())) {
                System.out.println("email adresa vec postoji!!!");
                return true;	
            }
        }
        return false;
    }
    
    
static Element newLeaf(Document doc, User user){
        Element noviUser = doc.createElement("user");
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
        noviUser.appendChild(nfirst_name);
        noviUser.appendChild(nlast_name);
        noviUser.appendChild(naddress);
        noviUser.appendChild(ncity);
        noviUser.appendChild(nemail);
        noviUser.appendChild(npassword);
        return noviUser;
        
    }

    
    static Element newMiddleNode(Document doc, User user) 
    {
        Element novaDrzava = doc.createElement("country");
        novaDrzava.setAttribute("name", user.getCountry());

        Element noviUser = newLeaf(doc, user);
    
        novaDrzava.appendChild(noviUser);
        return novaDrzava;
        
  }
  
    static Element newTopNode(Document doc, User user){
    Element noviKontinent = doc.createElement("continent"); 
    noviKontinent.setAttribute("name", user.getContinent());
    Element novaDrzava = newMiddleNode(doc, user);
    noviKontinent.appendChild(novaDrzava);
    return noviKontinent;	
    }
    
    static void ucitaj(User user, File putanja) throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
    
            Document doc = domBuilder.parse(putanja);
            
            Node root = doc.getDocumentElement();
            
                
            boolean indikatorDuplikata = check(doc, user) ;
            if(indikatorDuplikata==false) {	
                    NodeList list = root.getChildNodes();
                    for (int i = 0; i < list.getLength(); i++) {
                     // System.out.println("1. nivo	" + i);
                      Node childNode = list.item(i);
                      if(childNode.getNodeName() == "continent") {
                           NamedNodeMap nm = childNode.getAttributes();
                           for (int j = 0; j < nm.getLength(); j++) {
                               //System.out.println("Prolazim kroz atribute cvora " + i + " i redni broj atributa je " + j);
                                  Attr node =  (Attr)nm.item(j);
                                  if(node.getNodeValue().equals(user.getContinent())) {
                                      NodeList list2 = childNode.getChildNodes();
                                      for(int k = 0; k<list2.getLength(); k++) {
                                          //System.out.println("	2. nivo		" + k);
                                          Node grandchildNode = list2.item(k);
                                          if(grandchildNode.getNodeName() == "country") {
                                            NamedNodeMap nm2 = grandchildNode.getAttributes();
                                            for (int d = 0; d < nm2.getLength(); d++) {
                                                //	System.out.println("		3. nivo		" + d);
                                                    Attr node2 =  (Attr)nm2.item(d);
                                                    if(node2.getNodeValue().equals(user.getCountry())) {
                                                        Element noviUser = newLeaf(doc, user);
                                                        grandchildNode.appendChild(noviUser);
                                                    }
                                              }
                                            if(!countryExists(doc, user) && continentExists(doc, user) && !check(doc, user)) {
                                              System.out.println("Drzava ne postoji, treba da napravim drzavu");
                                              Element novaDrzava = newMiddleNode(doc, user);
                                                childNode.appendChild(novaDrzava);
                                            }
                                          }
                                       }
                                  }
                                  
                           }
                    }
             
                    }if(!continentExists(doc, user) && !check(doc, user)) {
                               System.out.println("Kontinent ne postoji, treba da napravim kontinent");
                                Element noviKontinent = newTopNode(doc, user);
                                    root.appendChild(noviKontinent);
                                    
                           }
                }
    
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            File xml = putanja;
            Result output = new StreamResult(xml); 
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
    }

}
