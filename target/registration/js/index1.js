const form1 = document.getElementById('form1')

if(form1!=null){
    form1.addEventListener('submit', fSubmit)
}else{
    console.log("fali formular")
}

function fSubmit(ev){
    //window.alert("Data sent!")
    //TODO obavesti korisnika sta se desilo
    let field
    field = document.getElementById("firstNameId")
   
    if(field==null){
        ev.preventDefault()
        field.focus()
        return;
    }
    let name  = field.value
    if(ime === ""){
        ev.preventDefault()
        return
    }



}

let cp = document.getElementById("cp");
let country = document.getElementById("country");
let children = country.children;
country.addEventListener("click", check);
let value = "France";
cp.style.backgroundImage= 'url("./img/france.jpg")';
cp.style.backgroundPosition = "20% 65%";

function check(){
    
    if(this.value == "United States" && this.value!=value){
        cp.style.backgroundImage= 'url("./img/us.jpg")';
        cp.style.backgroundPosition = "90% 20%";
    } else   if(this.value == "United Kingdom" && this.value!=value){
        cp.style.backgroundImage= 'url("./img/uk.jpg")';
        cp.style.backgroundPosition = "center center";
    }  else  if(this.value == "France" && this.value!=value){
        cp.style.backgroundImage= 'url("./img/france.jpg")';
        cp.style.backgroundPosition = "20% 65%";
    }  else  if(this.value == "Germany"&& this.value!=value){
        cp.style.backgroundImage= 'url("./img/germany.jpg")';
        cp.style.backgroundPosition = "center center";
    }  else  if(this.value == "Japan" && this.value!=value){
        cp.style.backgroundImage= 'url("./img/japan.jpg")';
        cp.style.backgroundPosition = "center center";
    }  else if(this.value == "China" && this.value!=value){
        cp.style.backgroundImage= 'url("./img/china.jpeg")';
        cp.style.backgroundPosition = "center center";
    }else if(this.value == "Canada" && this.value!=value){
        cp.style.backgroundImage= 'url("./img/canada.jpg")';
        cp.style.backgroundPosition = "center center";
    }

    value = this.value;
}


