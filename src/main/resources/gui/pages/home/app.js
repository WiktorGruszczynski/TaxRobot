const body = document.body;
const rows = document.getElementById("ul_users_rows");

const personalDataArray = [];


async function fetchRows(){
    const response =  await fetch("http://localhost:8080/api/personal_data/getAll", {
        headers:{
            "Access-Control-Allow-Origin": "*"
        }
    });

    console.log(response)
}

fetchRows()
