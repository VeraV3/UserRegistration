const form1 = document.getElementById('form1')

if(form1!=null){
    form1.addEventListener('submit', fSubmit)
}else{
    console.log("fali formular")
}

function fSubmit(ev){
    let name = document.getElementById("firstNameId")
   
    if(name==null){
        ev.preventDefault()
        name.focus()
        return;
    }
}