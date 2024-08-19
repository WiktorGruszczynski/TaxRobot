const body = document.body;
const users_list = document.getElementsByClassName("users")[0];
const personalDataOptionElement = document.getElementsByClassName("option")[0];
const wageStatementsOptionElement = document.getElementsByClassName("option")[1];
const dataContent = document.getElementsByClassName("data-content")[0];

var personalDataArray;
var userId;

var personalData;
var wageStatements;

function jsonField(label, value){

    return `<div class="field">
        <h4>"${label}":</h4>    
        <input value="${value}"/>
    </div>`
}

function handlePersonalDataClick(){
    const personalData = personalDataArray.find(p => p.id==userId)
    
    dataContent.innerHTML = (
        "<span>{</span>"+
        jsonField("id", personalData.id)+
        jsonField("name", personalData.name)+
        jsonField("vorname", personalData.vorname)+
        jsonField("strasse", personalData.strasse)+
        jsonField("nummer", personalData.nummer)+
        jsonField("zusatz", personalData.zusatz)+
        "<span>}</span>"
    );

}

function handleWageStatementsClick(){

}

async function fetchUsers(){
    const response = await fetch("http://localhost:8080/api/personal_data/getAll", {
        headers:{
            "Access-Control-Allow-Origin": "*"
        }
    });

    return await response.json();    
}

async function fetchWagestatements(){
    const response = await fetch("http://localhost:8080/api/wage_statement/"+userId)

    return await response.json();
}



function userListElement(user){
    const element = document.createElement("li");
    element.innerText = user.id;
    element.classList.add("user_li")

    element.onclick = () => {userId = user.id}

    users_list.appendChild(element)
}

function sidebar(users){
    users.map(user => 
        userListElement(user)
    )
}


async function fetchData(){
    personalDataArray = await fetchUsers();
}

async function render(){
    sidebar(personalDataArray);
}


async function main(){
    await fetchData();
    userId = personalDataArray[0].id;

    await render();
    personalDataOptionElement.click()
}

main()

