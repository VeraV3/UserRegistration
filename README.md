# UserRegistration
Overall, the backend of the web application includes several servlets that work together to provide functionality for user registration, displaying and deletion. The frontend includes an HTML form for user registration and a client-side script for displaying user information in a table and allowing users to delete entries.

Frontend:
The frontend consists of an HTML file that contains a user registration form and a list of registered users displayed in an iframe. The form includes input fields for the user's first name, last name, address, city, country, email, and password.

The user selects their country from a dropdown list that includes a list of countries, with "France" selected by default. The form is submitted to a servlet named "ServletSubmit" and dynamically changes the background image of an element based on the selected country value.

Additionally, there is an implementation of a client-side script for a web application that retrieves an XML document from the server, parses it, and displays the data in an HTML table. The table includes information about registered users, and each row has a link for deleting the corresponding user from the system.

Before deleting the entry, it prompts the user to confirm the deletion operation by displaying a dialog box with two buttons, "Yes" and "No." It then returns a boolean value indicating whether the user clicked the "Yes" button or not. If the user clicks "Yes," the entry is deleted from the XML file. If the user clicks "No," the entry is not deleted.

Backend:
The backend includes several servlets that work together to allow users to register and manage their information through a web application.

The SendXml servlet retrieves the data.xml file from the server's file system and sends it as a response to the client's HTTP GET request. Its purpose is to read an XML file and send it as a response to the client who made the request. The XML file is located in the WEB-INF directory of the web application, which is not directly accessible by clients. The servlet retrieves the file path using the getRealPath() method of the ServletContext object, which returns the filesystem path of a resource within the web application. It then creates a File object from the path and reads its contents using a BufferedInputStream and a FileInputStream. The contents are written to the response output stream, which is then sent back to the client with the appropriate HTTP headers indicating that the response contains an attachment named "data.xml" and its content type is "text/xml."

The ServletSubmit is a Java Servlet for handling registration form submissions. It creates a new User object with the retrieved data, and the DocumentBuilder is used to parse the XML file and create a new Document object representing the XML data. The load method then checks if the user's continent and country already exist in the XML data. If not, new continent and country elements are created and added to the Document object. Finally, a new user element is created and added to the country element (or the continent element, if the country element didn't previously exist), and the Document object is written back to the XML file.

The DeleteServlet is a Java servlet that handles HTTP GET requests and deletes an entry from an XML file containing user data based on the provided email address. The servlet receives the email address from the request parameter, opens the XML file, finds the entry with the matching email address, removes it from the XML document, and saves the changes to the XML
