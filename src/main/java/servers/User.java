package servers;

public class User {

    private String firstName ;
    private String lastName ;
    private String address;
    private String city;
    private String country;
    private String email ;
    private String password;
    private String continent;
    
    
    User(){}
    
    User(String fn, String ln, String add,  String city,  String country, String email,  String password ){
    	this.firstName = fn;
    	this.lastName = ln;
    	this.address= add;
    	this.city = city;
    	this.country = country;
    	this.email = email;
    	this.password  = password;
    	setContinent();
    }
    
    
    public String getFirstName() {
    	return this.firstName;
    }

    public String getLastName() {
    	return this.lastName;
    }

    public String getAddress() {
    	return this.address;
    }

    public String getCity() {
    	return this.city;
    }

    public String getCountry() {
    	return this.country;
    }

    public String getEmail() {
    	return this.email;
    }

    public String getPassword() {
    	return this.password;
    }
    
    public void setFirstName(String firstName) {
    	 this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
    	this.address  = address;
    }

    public void setCity(String city) {
    	this.city = city;
    }

    public void setCountry(String country) {
    	this.country = country;
    	setContinent();
    }

    public void setEmail(String email) {
    	this.email = email;
    }

    public void setPassword(String password) {
         this.password = password;
    }

    private void setContinent() {
    	if(this.country.equals("United Kingdom") || this.country.equals("France") || this.country.equals("Germany")) {
    		this.continent = "Europe";
    	}else if(this.country.equals("Japan") || this.country.equals("China")) {
    		this.continent = "Asia";
    	}else if(this.country.equals("United States")|| this.country.equals("Canada") ) {
    		this.continent = "North America";
    	}
    }
    
    @Override
   public  boolean equals(Object o) {
    	
    	if(o==this) {
    		return true;
    	}
    	
    	if(!(o instanceof User )) {
    		return false;
    	}
    	
    	User u = (User) o;
    	if(this.email.equals(u.getEmail()))
    		return true;
    	else
    		return false;
    }

    
    
    @Override
    public String toString() {
    	return firstName + " " + lastName+ " " + address + " " + city + " " +country + " " + " " + continent + " " + email + " " + password;
    	
    }

    public String getContinent() {
        return this.continent;
    }
    
}
