    let xhr = new XMLHttpRequest()
    //TODO promeni putanju
    xhr.open("GET", "./InitialData.xml")

    xhr.addEventListener("readystatechange", function(){
        switch(xhr.readyState){
            case XMLHttpRequest.DONE:
                if(xhr.status==200){
                    readXML(this)
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
       
        // let a = xmlResp.getElementsByTagName("first_name")[0].childNodes[0].nodeValue;

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

            // console.log(fn, ln, addr, cit, coun, em)
            // console.log("*************************")
        
        }
            
    let usersTable = document.getElementById("tab")

    createATable(usersTable, users)
      
    }

    
    function createATable(usersTable, users){
        for(user of users){
            makeARow(usersTable, user)
        }
    }

    /*
    f.onreset(event){
        let obrisi = window.confirm("Da li ste sigurni da zelite da obrisete?")
        if(obrisi){
            window.alert("Obrisano")
        }else{
            event.preventDefault()
        }
    }
    */

    // function deleteRow(event){
    //     let obrisi = window.confirm("Are you sure you want to delete?")
    //     if(obrisi){
    //         console.log(link.getParent)
    //         window.alert("Deleted!")
    //     }else{
    //        // event.preventDefault()
    //     }
    // }
    //TODO dohvatati redove tabele
    // let links = document.getElementsByTagName("a")
    // console.log(links)
    // let linkovi =  Array.from(links)
    // console.log(linkovi)
    // for(link of linkovi)
    //     console.log("ispisujem link: " + link)
    
    //link.offsetParent
    //links.addEventListener("click", deleteRow(this))

    function makeARow(usersTable, user){
        if (usersTable != null) {
                let tr = document.createElement('tr')
                usersTable.appendChild(tr)
                for (let key in user) {
                    let td = document.createElement('td')
                    let text = document.createTextNode(user[key])

                    if (key == "Action") {
                        let a = document.createElement('a')
                        a.href = ""
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