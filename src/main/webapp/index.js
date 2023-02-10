   
    let n = 2
    let users = []
    
    let user = {
        "Full name": "Vera Bogdanic", 
        "Address": "Vase Carapica 12", 
        "City" : "Banja Luka",
        "Country" : "BiH",
        "E-Mail" : "vera@gmail.com", 
        "Action" : "delete"
    }
    users.push(user)
    users.push(user)
    
    

    let usersTable = document.getElementById("tab")

    createATable(usersTable, users)

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
                        a.href = ""
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