    let xhr = new XMLHttpRequest()

    xhr.open("GET", "registration/", true) 

    xhr.responseType = 'document';

    xhr.addEventListener("readystatechange", function(){
        switch(xhr.readyState){
            case XMLHttpRequest.DONE:
                if(xhr.status==200){ 
                    if(this!= null){
                    readXML(this)
                    }
                    else{
                        console.log("objekat je null! nije primljen xml kako treba!")
                    }
                    return
                }
                if(xhr.status>=400){
                    console.log("xhr status je veci od 400 i greska je u ucitavanju xml-a u aplikaciju")
                    console.log(xhr.status, xhr.statusText)
                }
        }
    })

    xhr.send()

    function readXML(xml) {
        let n = 0
        let users = []
        var u, i, fn, ln, addr, cit, coun, em, xmlResp, user;
        xmlResp = xml.responseXML;
        u = xmlResp.getElementsByTagName('user');
        for (i = 0; i < u.length; i++) {
             fn = u[i].children[0].textContent;
             ln = u[i].children[1].textContent;
             addr  = u[i].children[2].textContent;
             cit = u[i].children[3].textContent;
             coun = u[i].parentNode.attributes.name.nodeValue
             em =u[i].children[4].textContent;
             user = {
                "Full name": fn+ " " + ln, 
                "Address": addr, 
                "City" : cit,
                "Country" : coun,
                "E-Mail" : em, 
                "Action" : "delete"
            }
            users.push(user)
            n++   
        }        
        let usersTable = document.getElementById("tab")
        createATable(usersTable, users)
    }

    function createATable(usersTable, users){
        for(user of users){
            makeARow(usersTable, user)
        }
    }
    
    function makeARow(usersTable, user){
        if (usersTable != null) {
                let tr = document.createElement('tr')
                usersTable.appendChild(tr)
                for (let key in user) {
                    let td = document.createElement('td')
                    let text = document.createTextNode(user[key])

                    if (key == "Action") {
                        let a = document.createElement('a')
                        a.href = "ServletDelete"
                        a.className = "links"
                        //a.addEventListener("click", deleteRow(this));
                        a.appendChild(text)
                        td.appendChild(a);
                    } else if (key == "Address") {
                        let address = document.createElement('address')
                        address.appendChild(text)
                        td.appendChild(address)
                    } else {
                        td.appendChild(text)
                    }

                    tr.appendChild(td)
                }
            
        }
    }

    function ask(){
        let obrisi = window.confirm("da ili ne")
        if(obrisi){
            window.alert("Odabrali ste da da")
        }else{
            window.alert("Odabrali ste da ne")
        }
    }
    
    //document.addEventListener("DOMContentLoaded", function(e) {
        //ask()
        var intervalID = window.setInterval(myCallback, 50)  //TODO: izbaci ovo i resi problem na drugi nacin
       
        function myCallback() {
            console.log("DOM fully loaded and parsed")
            // let links = document.getElementsByTagName("a")
            // console.log(links)
            let links = document.getElementsByClassName("links")
            console.log(links)
            console.log("pre petlje i duzina linkova je " + links.length)
            for(let i=0; i<links.length; i++){
                links[i].addEventListener("click", function(e2){
                    let parent  = links[i].parentNode
                    let sadrzaj = parent.previousSibling.textContent
                   // sadrzaj = sadrzaj.trim
                    console.log("sadrzaj elementa je je " + sadrzaj)
                    
            console.log("*********************************************************")
            let inputUrl = new URL('https://urlExample.com?key1=value1');
            let inputParams = new URLSearchParams(inputUrl.search);
            console.log("The parameters of the url is defined as: ", inputParams)
            inputParams.append('key2', 'value2');
            console.log("The url after adding adding the parameters is: ",inputParams)
            console.log("**********************************************************")
                    
            
                    links[i].href+="?"+"email="+sadrzaj
                    console.log(links)
                    //console.log("U funkciji sam za brisanje")
                    //console.log("this je  ovde " + this)  this je  ovde http://localhost:8080/registration/index2.html
                    let obrisi = window.confirm("Are you sure you want to delete?")
                    if(obrisi){
                        //console.log(links[i].parentNode.parentNode)
                        //let row = links[i].parentNode.parentNode
                        // let deleted = links[i].parentNode.parentNode.parentNode.removeChild(row)
                        //console.log("roditelj ovde je " +this.getParent)  undefined
                        window.alert("Deleted! " + sadrzaj)
                        }
                    else{
                        window.alert("Operation canceled")
                        }
                })
                //console.log("postavljen osluskivac za link "+ i )
            }
            //console.log("Posle svih linkova")
            clearInterval(intervalID)
        }
    //})


 