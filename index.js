   
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
    if(usersTable!=null){
        for(let i = 0; i<n; i++){
            let any = users[i]
            let tr = document.createElement('tr')
            usersTable.appendChild(tr)
            for(let key in users[i]){
                let th = document.createElement('th')
                let text = document.createTextNode(users[i][key])
            
                if(key=="Action"){
                    let a = document.createElement('a')
                    a.href = ""
                    a.appendChild(text)
                    th.appendChild(a);
                }else if(key=="Address"){
                    let address = document.createElement('address')
                    address.appendChild(text)
                    th.appendChild(address)
                }else{
                    th.appendChild(text)
                }
                
                tr.appendChild(th)
            }
        }
    }