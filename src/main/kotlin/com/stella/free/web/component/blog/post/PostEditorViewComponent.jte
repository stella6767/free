@import com.stella.free.core.blog.dto.PostDetailDto
@import com.stella.free.core.blog.entity.Post
@import java.util.List
@import java.util.Optional

@param Optional<PostDetailDto> post
@param String tagNames
@param Boolean isLogin
@param String username
@param Long userId

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/@yaireo/tagify"></script>
<link href="https://cdn.jsdelivr.net/npm/@yaireo/tagify/dist/tagify.css" rel="stylesheet" type="text/css"/>

<form class="py-4 flex flex-col gap-y-3 mb-3"
      @submit.prevent="submitForm"
      x-data="editorForm()"
>

    <h1 class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-gray-900 md:text-5xl lg:text-6xl dark:text-white">
        TOAST UI Editor
    </h1>

    <div class="flex justify-center gap-x-1 w-6/12 mx-auto">

        <input type="text"
               required
               disabled="${isLogin || (post.isPresent())}"
               x-model="formData.anonymousUsername"
               placeholder="input your username"
               class="input input-bordered w-full justify-self-center "/>


        <input type="text"
               x-model="formData.anonymousPassword"
               placeholder="input your password"
               class="input input-bordered w-full justify-self-center "/>

    </div>


    <div class="flex w-6/12 mx-auto justify-center">
        <input type="text"
               required
               x-model="formData.title"
               placeholder="input your title"
               class="input input-bordered justify-self-center w-full"/>
    </div>


    <div class="flex w-6/12 mx-auto justify-center">
        <input placeholder="태그를 입력하세요"
               name='hashtag'
               tabindex="2"
               class="input input-bordered justify-self-center w-full"
               value="">

    </div>

    <div id="editor">
    </div>

    <br/>

    <div class="flex justify-center gap-x-2">

        @if(post.isEmpty())
            <button class="btn btn-success">제출</button>
        @else
            <button class="btn btn-success">수정</button>
        @endif

        <button @click="cancelPost" class="btn btn-warning">취소</button>
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

    const input = document.querySelector('input[name=hashtag]');
    let tagify = new Tagify(input); // initialize Tagify

    const originTags = '${tagNames}'
    console.log("originTags", originTags)
    tagify.addTags(JSON.parse(originTags));

    tagify.on('add', function () {
        console.log(tagify.value); // 입력된 태그 정보 객체
    })

    const postId = '${post.map((p)-> p.getId()).orElse(0L)}'


    if (postId != 0) {
        console.log("postId!", postId)
    } else {
        console.log("postId??", postId)
    }

    function editorForm() {

        return {
            formData: {
                title: '${post.map((p)-> p.getTitle()).orElse("")}',
                anonymousUsername: '${post.map((p)-> p.getUsername()).orElse(username)}',
                anonymousPassword: '',
                content: "",
                postTags: null,
            },
            submitForm() {
                //let req = JSON.stringify(this.formData);
                this.formData.content = editor.getHTML();

                let headers = {
                    'Content-Type': 'application/json'
                }

                const tagNames = tagify.value.map((el) => {
                    return el.value
                });

                console.log("tagNames", tagNames)
                this.formData.postTags = tagNames;

                if (postId != 0) {

                    this.formData.id = postId
                    let reqBody = JSON.stringify(this.formData);

                    client('/post', {body: reqBody, headers: headers, method: 'PUT'}).then(
                        data => {
                            console.log('here is response', data.body)
                            //location.href = "/blog";
                        },
                        error => {
                            console.error('oh no,  failed', error)
                        },
                    )
                } else {

                    let reqBody = JSON.stringify(this.formData);

                    client('/post', {body: reqBody, headers: headers}).then(
                        data => {
                            location.href = "/blog";
                        },
                        error => {
                            console.error('oh no,  failed', error)
                        },
                    )

                }


            }

        }
    }


    const cancelPost = () => {
        location.href = "/blog"
    }


    let contentValue = '${post.map((p)-> p.getContent()).orElse("내용을 입력해주세요")}';

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


</script>
