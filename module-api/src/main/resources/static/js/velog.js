let isProcess = false


function arrayBufferToString(buffer) {
    return decodeURIComponent(String.fromCharCode.apply(null, Array.from(new Uint8Array(buffer))));
}

document.getElementById("downloader").addEventListener("click", function (event) {

    document.getElementById('loading-spinner').style.display = 'inline';
    console.log("클릭됨")

    if (isProcess){
        console.log("진행중이라 just return")
        return
    }

    // Get values from the select and input elements
    //const type = document.getElementById('type').value;
    const username = document.getElementById('username').value;
    // Construct the URL with parameters
    const apiUrl = "/velog?username=" + username ;

    const req = new XMLHttpRequest();
    req.open("GET", apiUrl, true);
    isProcess = true

    req.responseType = "arraybuffer";

    req.onreadystatechange = function () {
        if (req.status != 200) {
            console.error(req)
            alert(arrayBufferToString(req.response))
        }
    }

    req.onload = function () {
        const arrayBuffer = req.response;
        if (arrayBuffer) {
            var blob = new Blob([arrayBuffer], {type: "application/zip"});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.target="_blank"
            link.download = "velog.zip";
            link.click();
            document.getElementById('loading-spinner').style.display = 'none';
            isProcess = false
        }
    };
    req.send();


    //event.target.setAttribute("href", apiUrl);
    // Hide the loading spinner after a delay (adjust as needed)
    // setTimeout(() => {
    //     document.getElementById('loading-spinner').style.display = 'none';
    // }, 2000); // 2000 milliseconds (2 seconds) as an example

});