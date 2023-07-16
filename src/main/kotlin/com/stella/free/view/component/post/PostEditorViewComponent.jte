@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.context.SecurityContextHolder

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

<form class="py-4 flex flex-col gap-y-3 mb-3"
      @submit.prevent="submitForm"
      x-data="editorForm()"
>

    <h1 class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-gray-900 md:text-5xl lg:text-6xl dark:text-white">
        TOAST UI Editor
    </h1>


    <divc class = "flex justify-center">
        @if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
            <input type="text"
                   x-model="formData.username"
                   placeholder="input your username"
                   class="input input-bordered w-64 justify-self-center "/>
        @endif

    </divc>


    <div class="flex justify-center">
        <input type="text"
               required
               x-model="formData.title"
               placeholder="input your title"
               class="input input-bordered w-64 justify-self-center"/>
    </div>


    <div id="editor"></div>

    <br/>

    <div  class="flex justify-center gap-x-2">
        <button class="btn btn-success">제출</button>
        <button class="btn btn-warning">취소</button>
    </div>


</form>


<style>
    h1 {
        text-align: center;
    }

    #editor {
        border: 1px solid;
        width: 50%;
        margin: 0 auto;
    }

</style>


<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<script>


    function editorForm() {

        return {

            formData: {
                title: "",
                username: "",
                content: editor.getMarkdown(),
                anonymousUsername: ""
            },
            submitForm() {
                let body = JSON.stringify(this.formData);

                console.log("submit", body);

                client('/blog/post', 'POST', {body: body}).then(
                    data => {
                        console.log('here the logged in user data', data)
                    },
                    error => {
                        console.error('oh no,  failed', error)
                    },
                )

            }

        }
    }




    const submitPost = (e) => {

        console.log("click submit", editor.getMarkdown())

        let reqBody = {
            title: "",
            content: editor.getMarkdown(),
            thumbnails: "",
            anonymousUsername: ""
        }


        // client('login', {body: {username, password}}).then(
        //     data => {
        //         console.log('here the logged in user data', data)
        //     },
        //     error => {
        //         console.error('oh no, login failed', error)
        //     },
        // )


    }


    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        previewStyle: 'vertical',
        initialEditType: 'markdown',
        height: '1000px',
        theme: 'dark',
        initialValue: '내용을 입력해주세요',
        events: {
            change: function () {
                //console.log(editor.getMarkdown());
            }
        },
        hooks: {
            addImageBlobHook: (blob, callback) => {
                // blob : Java Script 파일 객체
                //console.log(blob);
                const formData = new FormData();
                formData.append('file', blob);

                // htmx.ajax('POST', '/blog/file', '#myDiv').then(() => {
                //     //이거는 한계가 있네..
                //     console.log('Content inserted successfully!');
                // });

                fetch("/blog/file", {
                    method: "POST",
                    headers: {
                        //"Content-Type": "multipart/form-data", 생략해야 동작.. 진짜 이상하네?
                    },
                    body: formData,
                }).then((response) => {
                    return response
                }).then((res) => {
                    console.log(res)
                    callback(res, '사진 대체 텍스트 입력');
                }).catch((err) => callback('image_load_fail', '사진 대체 텍스트 입력'));

                // $.ajax({
                //     type: 'POST',
                //     enctype: 'multipart/form-data',
                //     url: '/files/upload',
                //     data: formData,
                //     dataType: 'json',
                //     processData: false,
                //     contentType: false,
                //     cache: false,
                //     timeout: 600000,
                //     success: function (data) {
                //         //console.log('ajax 이미지 업로드 성공');
                //         url += data.fileName;
                //         fileIds += data.id + ",";
                //         // callback : 에디터(마크다운 편집기)에 표시할 텍스트, 뷰어에는 imageUrl 주소에 저장된 사진으로 나옴
                //         // 형식 : ![대체 텍스트](주소)
                //         callback(url, '사진 대체 텍스트 입력');
                //     },
                //     error: function (e) {
                //         //console.log('ajax 이미지 업로드 실패');
                //         //console.log(e.abort([statusText]));
                //
                //         callback('image_load_fail', '사진 대체 텍스트 입력');
                //     }
                // });
            }
        }

    });

    // document.querySelector('#contents').insertAdjacentHTML('afterbegin' ,editor.getHTML());
    // console.log(editor.getHTML());

</script>

</script>