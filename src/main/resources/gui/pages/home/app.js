const body = document.body;
const users_list = document.getElementsByClassName("users")[0];
const personalDataOptionElement = document.getElementsByClassName("option")[0];
const wageStatementsOptionElement = document.getElementsByClassName("option")[1];
const dataContent = document.getElementsByClassName("data-content")[0];
const dataEl = document.getElementsByClassName("data")[0]
const requestInfo = document.getElementById("request-info")

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
    PID: "",
    pensionskasse: false,
    gemeinde: "",
    gemeinde2: ""
}

const wageStatementsScheme = {
    von: "",
    bis: "",
    arbeitGeber: "",
    nettolohn: 0
}



function handleFieldClick(e){
    const target = e.target;

    if (target.getAttribute("isNull")==="true"){
        const parent = target.parentElement;
        const color_class = "json-green";
        const label = e.target.getAttribute("label");
    
        var value = "";
        
       
        if (e.inputType = "insertText"){
            if (e.data){
                value = e.data
            }
            else{
                data = ""
            }
        }

        parent.innerHTML = (
            `<div class=${color_class}>"</div>
            <div class="json-input value ${color_class}" contenteditable label=${label} spellcheck="false" oninput=handleFieldClick(event)>${value}</div>
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
        <div class="${color_class} lq">${sp_char}</div>
        <div class="json-input value ${color_class}" label=${label} isNull=${isNull} type=${type}  ${editable?"contenteditable":""} spellcheck="false" oninput=handleFieldClick(event)>
            ${value}
        </div>
        <div class="${color_class} rq">${sp_char}</div>    
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
        '<h3>Personal data</h3>'+
        '<span class="json-blue j-bracket">{</span>'+
        jsonField("id", personalData.id, false)+
        jsonField("name", personalData.name)+
        jsonField("vorname", personalData.vorname)+
        jsonField("strasse", personalData.strasse)+
        jsonField("nummer", personalData.nummer)+
        jsonField("zusatz", personalData.zusatz)+
        jsonField("PLZ", personalData.PLZ)+
        jsonField("ort", personalData.ort)+
        jsonField("geburtsdatum", personalData.geburtsdatum?personalData.geburtsdatum.split("T")[0]:null)+
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


function addWageStatement(){
    wageStatements.push(wageStatementsScheme)
    renderWageStatements()
}

function removeWageStatement(target){
    const index = Number(target.getAttribute("index"));
    const found_el = [... dataContent.getElementsByClassName("arr-el")]
        .find(el => Number(el.getAttribute("index"))===index );

    
    wageStatements.splice(index, 1)
    found_el?.remove()

    

    if (dataContent.getElementsByClassName("arr-el").length == 0){
        document.getElementById("json-array").innerHTML = arrSeparator(0)
    }
}

function handleSeparatorInput(e){
    const key = e.key;
    const target = e.target;

    wageStatements = getJsonArray(document.getElementsByClassName("arr-el"))

    if (key === "Backspace"){
        removeWageStatement(target)
    }

    if (key === "Enter"){
        addWageStatement(target)
    }

    target.innerText = ","
    
    e.preventDefault()
}

function arrSeparator(index){
    return `<span class="json-blue arr-add-btn" dir="rtl" index=${index} onkeydown={handleSeparatorInput(event)} contenteditable>,</span>`
}

function renderWageStatements(){
    var ctx = "";
    let counter = 0;

    wageStatements.forEach(element => {
        let buffer = `<div class="arr-el" index=${counter}><span class="json-blue j-bracket">{</span>`
            + jsonField("von", element.von)
            + jsonField("bis", element.bis)
            + jsonField("arbeitGeber", element.arbeitGeber)
            + jsonField("nettolohn", element.nettolohn)
            + '<span class="json-blue j-bracket">}</span>'
            + arrSeparator(counter)
            + '</div>'
            

        ctx += buffer
        counter+=1
    });

    
    if (wageStatements.length == 0){
        ctx = arrSeparator(0)
    }

    dataContent.innerHTML = (
        '<h3>Wage statements</h3>'+
        '<span class="json-blue j-bracket">[</span>'+
        '<span id="json-array">'+
        ctx +
        '</span>'+
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

    if (response.status === 200){
        requestInfo.innerText = "Succes"

        setInterval(()=>{
            requestInfo.innerText = ""
        }, 2500)
    }
}


function handleUserIdClick(id){
    userId = id;
    handlePersonalDataClick(personalDataArray)
}

function createEmptyUserForm(){
    personalData = personalDataScheme;
    renderPersonalData()
}

function valueFromField(field, nullStr=""){
    var value = field.innerText.replaceAll("\n","")
    const type = field.getAttribute("type")


    if (type === "number"){
        value = Number(value)
    }

    if (type === "boolean"){
        value = value==="true"?true:false
    }


    if (field.getAttribute("isNull")==="true"){
        value = null
    }

    if (type === "string"){
        if (value === ""){
            value = nullStr
        }
    }

    return value;

}

function getJson(){
    const fields = [... dataContent.getElementsByClassName("field")]
    const result = {};

    console.log(fields)

    fields.forEach(field => {
        var key = field.getElementsByClassName("key")[0].innerText.slice(1,-1)
        var value_field = field.getElementsByClassName("value")[0]


        var value = valueFromField(value_field, "")

        result[key] = value
    })

    return result;
}

function getJsonArray(elemets, nullStr=""){
    const result = [];
    var arr = [... elemets]

    arr.forEach(el => {
        const obj = {};

        const fields = [... el.getElementsByClassName("field")]
        fields.forEach(field => {
            var key = field.getElementsByClassName("key")[0].innerText.slice(1,-1)
            var value_field = field.getElementsByClassName("value")[0]

            var value = valueFromField(value_field, nullStr)

            
            obj[key] = value
        })

        result.push(obj)
    })

    return result;
}

function getWagestatementsFromHtml(){
    return [...(wage_statements.map(el => ({
        ...el,
        personalDataId: userId
    })))]
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

    if (response.status == 200){
        wageStatements = await fetchWagestatements()
    }
}

async function updatePersonalData(personal_data){
    const response = await fetch("http://localhost:8080/api/personal_data", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(personal_data)
    })
}


async function saveUpdate(){
    var arr_elements = dataContent.getElementsByClassName("arr-el");
    var title = dataContent.getElementsByTagName("h3")[0].innerText;
    var dataToSend;

    if (arr_elements.length === 0){
        dataToSend = getJson()
    }
    else{
        dataToSend = getJsonArray(arr_elements, null)
    }


    if (title === "Wage statements"){
        await updateWageStatements(dataToSend)
    }

    if (title === "Personal data"){
        await updatePersonalData(dataToSend)
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
    idList = [... idList].sort()
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

