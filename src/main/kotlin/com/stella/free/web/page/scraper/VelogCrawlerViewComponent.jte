
@import java.util.List
@param String introduction


<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

<div class="container mx-auto px-4 bg-white">


    <%--    <form hx-post="/dummy" hx-target="#dummy-table" hx-indicator="#loading-spinner">--%>
    <%--    <form hx-post="/velog" hx-target="#dummy-table" hx-swap="none" hx-indicator="#loading-spinner">--%>

    <div class="flex justify-between mb-5">
        <%--            <input type="number" name="size" placeholder="number" class="input input-bordered w-full max-w-xs"/>--%>
        <input id="username" name="username" placeholder="input username"
               class="input input-bordered w-full max-w-xs mt-3" required/>
<%--        <select id="type" class="select select-ghost w-full max-w-xs" name="type" required>--%>
<%--            <option disabled selected>Pick the Type</option>--%>
<%--            <option value="COROUTINE">Coroutine</option>--%>
<%--            <option value="TASK">Thread Pool</option>--%>
<%--            <option value="SINGLE">Single Thread</option>--%>
<%--        </select>--%>
        <button>
<%--            <a id="downloader" href="#" download="velog.zip" target="_blank">가져오기</a>--%>
            <a id="downloader" href="#" class="font-extrabold">가져오기</a>
        </button>
    </div>
    <%--    </form>--%>

    <%--    ${commonTableViewComponent.render(List.of())}--%>

    <%-- if 문으로 사용법 보여주다가, 글 가져오면 감추기 --%>
    <div class="toastui-editor-contents" style="overflow-wrap: break-word;">
        <div data-nodeid="1">
            $unsafe{introduction}
        </div>
    </div>


</div>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script>

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


</script>







