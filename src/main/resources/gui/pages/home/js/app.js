const body = document.body;
const users_list = document.getElementsByClassName("users")[0];
const personalDataOptionElement = document.getElementsByClassName("option")[0];
const wageStatementsOptionElement = document.getElementsByClassName("option")[1];
const dataContent = document.getElementsByClassName("data-content")[0];

var personalDataArray;
var userId;

var personalData;
var wageStatements;


function handleFieldClick(e){
    const target = e.target;

    if (target.getAttribute("isNull")==="true"){
        const parent = target.parentElement;
        const color_class = "json-green";
        const label = e.target.getAttribute("label");
    
        var value = "";
        
        if (e.inputType = "insertText"){
            value = e.data;
        }
    
        parent.innerHTML = (
            `<div class=${color_class}>"</div>
            <div class="json-input ${color_class}" contenteditable label=${label} spellcheck="false" oninput=handleFieldClick(event)>${value}</div>
            <div class=${color_class}>"</div>`
        )
        
        const child = parent.children[1];
        child.focus()
    }


   
}


function fieldBuilder(label, value, editable, sp_char, isNull, color_class){
    return `<div class="field">
    <h4 class="json-blue">"${label}"</h4>
    <h4>:</h4>    
    <div class="json-input-wrapper">
        <div class=${color_class}>${sp_char}</div>
        <div class="json-input ${color_class}" label=${label} isNull=${isNull}  ${editable?"contenteditable":""} spellcheck="false" oninput=handleFieldClick(event)>
            ${value}
        </div>
        <div class=${color_class}>${sp_char}</div>    
    </div>
</div>`
}


function jsonField(label, value, editable=true){
    var color_class = "json-blue";
    var sp_char = "";
    var isNull = false;

    if (typeof(value) === "string"){
        sp_char = '"';
        color_class = "json-green"
    }

    if (typeof(value) === "number"){
        color_class = "json-orange"
    }

    if (typeof(value) === "boolean"){
        color_class = "json-purple"
    }

    if (value===null){
        color_class = "json-orange"
        isNull = true
    }

    return fieldBuilder(label, value, editable, sp_char, isNull, color_class)
}

function handlePersonalDataClick(){
    const personalData = personalDataArray.find(p => p.id==userId)
    
    dataContent.innerHTML = (
        '<span class="json-blue j-bracket">{</span>'+
        jsonField("id", personalData.id, false)+
        jsonField("name", personalData.name)+
        jsonField("vorname", personalData.vorname)+
        jsonField("strasse", personalData.strasse)+
        jsonField("nummer", personalData.nummer)+
        jsonField("zusatz", personalData.zusatz)+
        jsonField("PLZ", personalData.PLZ)+
        jsonField("ort", personalData.ort)+
        jsonField("geburtsdatum", personalData.geburtsdatum.split(" ")[0])+
        jsonField("AHVN13", personalData.AHVN13)+
        jsonField("zivilstand", personalData.zivilstand)+
        jsonField("konfession", personalData.konfession)+
        jsonField("beruf", personalData.beruf)+
        jsonField("telefon", personalData.telefon)+
        jsonField("email", personalData.email)+
        jsonField("PID", personalData.PID)+
        jsonField("pensionskasse", personalData.pensionskasse)+
        jsonField("gemeinde", personalData.gemeinde)+
        jsonField("gemeinde2", personalData.gemeinde2)+
        '<span class="json-blue j-bracket">}</span>'
    );

}

async function handleWageStatementsClick(){
    const wage_statements = await fetchWagestatements();
    var ctx = "";

    wage_statements.forEach(element => {
        let buffer = '<div class="arr-el"><span class="json-blue j-bracket">{</span>'
            + jsonField("von", element.von)
            + jsonField("bis", element.bis)
            + jsonField("arbeitGeber", element.arbeitGeber)
            + jsonField("nettolohn", element.nettolohn)

            + '<span class="json-blue j-bracket">}</span></div>'

        ctx += buffer
    });

    dataContent.innerHTML = (
        '<span class="json-blue j-bracket">[</span>'+
        ctx +
        '<span class="json-blue j-bracket">]</span>'
    )
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
    const response = await fetch("http://localhost:8080/api/wage_statement/getById/"+userId)

    return await response.json();
}


function handleUserIdClick(id){
    userId = id;
    handlePersonalDataClick(personalDataArray)
}



function userListElement(user){
    const element = document.createElement("li");
    element.innerText = user.id;
    element.classList.add("user_li")

    element.onclick = () => handleUserIdClick(user.id)

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
    handlePersonalDataClick()
}

main()

