const body = document.body;
const users_list = document.getElementsByClassName("users")[0];
const personalDataOptionElement = document.getElementsByClassName("option")[0];
const wageStatementsOptionElement = document.getElementsByClassName("option")[1];
const dataContent = document.getElementsByClassName("data-content")[0];
const dataEl = document.getElementsByClassName("data")[0]

var idList;
var personalDataArray;
var userId;

var personalData;
var wageStatements;


const personalDataScheme = {
    id: null,
    name: "",
    vorname: "",
    strasse: "",
    nummer: "",
    zusatz: "",
    PLZ: "",
    ort: "",
    geburtsdatum: "",
    AHVN13: "",
    zivilstand: "",
    konfession: "",
    beruf: "",
    telefon: "",
    email: "",
    pensionskasse: false,
    gemeinde: "",
    gemeinde2: ""
}



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


    if (e.inputType === "insertParagraph"){
        target.innerHTML = target.innerText.replaceAll("\n","")
    }

   
}


function fieldBuilder(label, value, editable, sp_char, isNull, color_class, type){
    return `<div class="field">
    <h4 class="json-blue key">"${label}"</h4>
    <h4>:</h4>    
    <div class="json-input-wrapper">
        <div class="${color_class}">${sp_char}</div>
        <div class="json-input value ${color_class}" label=${label} isNull=${isNull} type=${type}  ${editable?"contenteditable":""} spellcheck="false" oninput=handleFieldClick(event)>
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

    let type = typeof(value);
    if (value === null){
        type = "string"
    }

    return fieldBuilder(label, value, editable, sp_char, isNull, color_class, type)
}


function renderPersonalData(){
    dataContent.innerHTML = (
        '<h3>Personal Data</h3>'+
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

async function fetchPersonalData(id){
    const response = await fetch("http://localhost:8080/api/personal_data/getById/"+id);

    return await response.json();
}

async function handlePersonalDataClick(){
    personalData = await fetchPersonalData(userId)
    renderPersonalData()
}

function addWageStatement(e){
    if (e.inputType != "insertParagraph"){
        e.target.innerHTML = ",";
        return; 
    }

    wageStatements.push({
        von: "",
        bis: "",
        arbeitGeber: "",
        nettolohn: 0
    })

    renderWageStatements()
}


function renderWageStatements(){
    var ctx = "";

    wageStatements.forEach(element => {
        let buffer = '<div class="arr-el"><span class="json-blue j-bracket">{</span>'
            + jsonField("von", element.von)
            + jsonField("bis", element.bis)
            + jsonField("arbeitGeber", element.arbeitGeber)
            + jsonField("nettolohn", element.nettolohn)
            + '<span class="json-blue j-bracket">}</span>'
            + '<span class="json-blue arr-add-btn" oninput={addWageStatement(event)} contenteditable>,</span>'
            + '</div>'
            

        ctx += buffer
    });

    
    if (wageStatements.length == 0){
        ctx = '<span class="json-blue arr-add-btn" oninput={addWageStatement(event)} contenteditable>,</span>'
    }

    dataContent.innerHTML = (
        '<h3>Wage statements</h3>'+
        '<span class="json-blue j-bracket">[</span>'+
        ctx +
        '<span class="json-blue j-bracket">]</span>'
    )
}

async function handleWageStatementsClick(){
    wageStatements = await fetchWagestatements();
    renderWageStatements()

}

async function fetchIds(){
    const response = await fetch("http://localhost:8080/api/personal_data/getAllIds", {
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

async function saveWageStatements(){
    const response = await fetch("http://localhost:8080/api/wage_statement", {
        method: "POST",
        body: JSON.stringify(wageStatements)
    })
}


function handleUserIdClick(id){
    userId = id;
    handlePersonalDataClick(personalDataArray)
}

function createEmptyUserForm(){
    personalData = personalDataScheme;
    renderPersonalData()
}

function saveJson(){

}

function saveJsonArray(elemets){
    const result = [];
    var arr = [... elemets]

    arr.forEach(el => {
        const obj = {};

        const fields = [... el.getElementsByClassName("field")]
        fields.forEach(field => {
            var key = field.getElementsByClassName("key")[0].innerText.slice(1,-1)
            var value_field = field.getElementsByClassName("value")[0]

            value = value_field.innerText.replaceAll("\n","")

            const type = value_field.getAttribute("type")

            if (type === "number"){
                value = Number(value)
            }

            if (value_field.getAttribute("isNull")){
                value = null
            }
            
            obj[key] = value
        })

        result.push(obj)
    })

    return result;
}


async function updateWageStatements(wage_statements){
    var post_data = [...(wage_statements.map(el => ({
        ...el,
        personalDataId: userId
    })))]

    const response = await fetch("http://localhost:8080/api/wage_statement", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(post_data)
    })
}

async function saveUpdate(){
    var arr_elements = dataContent.getElementsByClassName("arr-el")
    var title = dataContent.getElementsByTagName("h3")[0].innerText
    var dataToSend;

    if (arr_elements.length === 0){
        saveJson()
    }
    else{
        dataToSend = saveJsonArray(arr_elements)
    }


    if (title === "Wage statements"){
        await updateWageStatements(dataToSend)
    }


}


function userListElement(id){
    const element = document.createElement("li");
    element.innerText = id;
    element.classList.add("user_li")

    element.onclick = () => handleUserIdClick(id)

    users_list.appendChild(element)
}


function addUserBtn(){
    const element = document.createElement("li")
    element.innerText = "Add user";
    element.classList.add("user_li")

    element.onclick = () => createEmptyUserForm()

    return element;
}

function sidebar(ids){
    ids.map(id => 
        userListElement(id)
    )
    
    users_list.appendChild(addUserBtn())
}


async function fetchData(){
    idList = await fetchIds();
}

async function render(){
    sidebar(idList);
}


async function main(){
    await fetchData();
    userId = idList[0];

    await render();
    await handlePersonalDataClick()
}

main()

