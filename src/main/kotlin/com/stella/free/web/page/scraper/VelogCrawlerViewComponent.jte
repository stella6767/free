@import com.stella.free.web.component.table.CommonTableViewComponent
@import java.util.List

@param CommonTableViewComponent commonTableViewComponent

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

<div class="container mx-auto px-4">


    <%--    <form hx-post="/dummy" hx-target="#dummy-table" hx-indicator="#loading-spinner">--%>



    <%--    <form hx-post="/velog" hx-target="#dummy-table" hx-swap="none" hx-indicator="#loading-spinner">--%>
    <div class="flex justify-between mb-5">
        <%--            <input type="number" name="size" placeholder="number" class="input input-bordered w-full max-w-xs"/>--%>
        <input id="username" name="username" placeholder="input username"
               class="input input-bordered w-full max-w-xs" required/>
        <select id="type" class="select select-ghost w-full max-w-xs" name="type" required>
            <option disabled selected>Pick the Type</option>
            <option value="COROUTINE">Coroutine</option>
            <option value="TASK">Thread Pool</option>
            <option value="SINGLE">Single Thread</option>
        </select>
        <button>
<%--            <a id="downloader" href="#" download="velog.zip" target="_blank">가져오기</a>--%>
            <a id="downloader" href="#" >가져오기</a>
        </button>
    </div>
    <%--    </form>--%>

    <%--    ${commonTableViewComponent.render(List.of())}--%>

    <%-- if 문으로 사용법 보여주다가, 글 가져오면 감추기 --%>
    <div class="toastui-editor-contents" style="overflow-wrap: break-word;">
        <div data-nodeid="1">
            <h1>velog Cralwer 사용법</h1>
            <h2>velog의 GraphQl API를 이용해 사용자의 정보를 수집합니다.</h2>
            <p>
                <img alt="사진 대체 텍스트 입력" src="https://free-stella.s3.ap-northeast-2.amazonaws.com/171151e6-5793-4be1-91b4-7c5791b17c2e_velog-introduction1.png">
                <br>
            </p>
            <h2>Velog Username 을 입력합니다.</h2>
            <p>버튼을 누르면, ZIP 파일 형태로 입력한 유저네임의 Velog 글들을 가져올 수 있습니다.</p>
            <p>
                <br>
            </p>
            <p>추후 몇가지 기능 개발 여부 생각중..</p>
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
        const type = document.getElementById('type').value;
        const username = document.getElementById('username').value;
        // Construct the URL with parameters
        const apiUrl = "/velog?username=" + username + "&type=" + type;

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







