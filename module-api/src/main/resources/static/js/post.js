(function () {


    const input = document.querySelector('input[name=hashtag]');

    let tagify = new Tagify(input); // initialize Tagify

    let originTags = document.querySelector('input[name=originTags]').value;
    let postId = document.querySelector('input[name=postId]').value;

    console.log("postId", postId)
    console.log("originTags", originTags)

    tagify.addTags(JSON.parse(originTags));
    tagify.on('add', function () {
        console.log(tagify.value); // 입력된 태그 정보 객체
    })


    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        previewStyle: 'vertical',
        initialEditType: 'markdown',
        height: '1000px',
        //theme: 'dark',
        initialValue: contentValue,
        events: {
            change: function () {
                console.log(editor.getMarkdown());
                // console.log(editor.getHTML());

            }
        },
        hooks: {
            addImageBlobHook: (blob, callback) => {
                // blob : Java Script 파일 객체
                //console.log(blob);

                const formData = new FormData();
                formData.append('file', blob);

                client('/post/file', {body: formData, method: 'POST'}).then(
                    data => {
                        console.log("data", data)
                        callback(data, '사진 대체 텍스트 입력');
                    },
                    error => {
                        console.error('oh no,  failed', error)
                        callback('image_load_fail', '사진 대체 텍스트 입력')
                    },
                )
            }
        }

    });

    function cancelPost ()  {
        location.href = "/blog"
    }


    document.getElementById("post-submit-btn").addEventListener("click", (e) => {

        e.preventDefault();

        let formData = {
            title: "",
            username: "",
            content: "",
            postTags: null,
        }

        formData.content = editor.getHTML();

        formData.title = document.querySelector('input[name="title"]').value;
        formData.username = document.querySelector('input[name="username"]').value


        let headers = {
            'Content-Type': 'application/json'
        }
        const tagNames = tagify.value.map((el) => {
            return el.value
        });

        console.log("tagNames", tagNames)
        formData.postTags = tagNames;

        let alertMsg = ""
        let method = ""

        if (postId != 0) {
            formData.id = postId;
            alertMsg = "수정에 실패하였습니다. 비밀번호를 확인해주세요";
            method = "PUT";
        }else{
            alertMsg = "출간 실패";
            method = "POST"
        }

        let reqBody = JSON.stringify(formData);
        client('/post', {body: reqBody, headers: headers, method: method}).then(
            data => {
                console.log("data", data)

                location.href = "/page/post/" + data;
            },
            error => {
                console.error('oh no,  failed', error);
                alert(alertMsg);
            },
        )

    })
})();









