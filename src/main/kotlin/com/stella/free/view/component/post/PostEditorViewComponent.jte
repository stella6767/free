


<div class="bg-slate-100">
    <h1> TOAST UI Editor </h1>

    <div id="editor"  style="color: #fff"></div>

</div>





<style>
    * {
        text-align:  center;
    }
    #editor {
        border : 1px solid;
        width : 50%;
        margin : 0 auto;
    }
</style>

<script>

    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        previewStyle: 'vertical',
        initialEditType: 'markdown',
        theme: 'dark',
        height: '1000px',
        initialValue: '내용을 입력해주세요',
        events: {
            change: function () {
                console.log(editor.getMarkdown());
            }
        },

        hooks: {
            addImageBlobHook: (blob, callback) => {
                // blob : Java Script 파일 객체
                //console.log(blob);

                const formData = new FormData();
                formData.append('file', blob);

                let url = '/files/';
                $.ajax({
                    type: 'POST',
                    enctype: 'multipart/form-data',
                    url: '/files/upload',
                    data: formData,
                    dataType: 'json',
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {
                        //console.log('ajax 이미지 업로드 성공');
                        url += data.fileName;
                        fileIds += data.id + ",";
                        // callback : 에디터(마크다운 편집기)에 표시할 텍스트, 뷰어에는 imageUrl 주소에 저장된 사진으로 나옴
                        // 형식 : ![대체 텍스트](주소)
                        callback(url, '사진 대체 텍스트 입력');
                    },
                    error: function (e) {
                        //console.log('ajax 이미지 업로드 실패');
                        //console.log(e.abort([statusText]));

                        callback('image_load_fail', '사진 대체 텍스트 입력');
                    }
                });
            }
        }

    });

    // document.querySelector('#contents').insertAdjacentHTML('afterbegin' ,editor.getHTML());
    // console.log(editor.getHTML());

</script>