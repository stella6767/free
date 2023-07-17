const {Editor: Editor} = toastui, editor = new Editor({
    el: document.querySelector("#editor"),
    previewStyle: "vertical",
    height: "500px",
    initialValue: document.getElementById("content").value,
    previewHighlight: !1,
    hooks: {
        addImageBlobHook: async (e, t) => {
            console.log(e);
            var n = new FormData;
            n.append("image", e), $.ajax({
                type: "POST",
                enctype: "multipart/form-data",
                url: "/s3/image",
                data: n,
                mimeType: "multipart/form-data",
                processData: !1,
                contentType: !1,
                cache: !1,
                timeout: 6e5,
                success: function (e) {
                    console.log(e), t(e, "이미지")
                },
                error: function (e) {
                    t("image_load_fail", "이미지업로드 실패")
                }
            })
        }
    }
});

function mdGet() {
    const e = document.getElementById("title").value;
    if (null == e || "" === e.trim()) return notification("제목이 비어있습니다."), !1;
    const t = document.getElementById("selected_location").value;
    if (null == t || "" === t.trim()) return notification("위치를 선택해주세요."), !1;
    document.getElementById("content").value = editor.getHTML()
}

function notification(e) {
    Swal.mixin({
        toast: !0,
        position: "top-end",
        showCloseButton: !0,
        showConfirmButton: !1,
        timer: 3e3,
        timerProgressBar: !0,
        didOpen: e => {
            e.addEventListener("mouseenter", Swal.stopTimer), e.addEventListener("mouseleave", Swal.resumeTimer)
        }
    }).fire({icon: "warning", background: "#e76876", title: '<h4 style="color: white;">' + e + "</h4>"})
}

const thumbnail = document.getElementById("uploadThumbnail");
thumbnail.addEventListener("change", uploadThumbnail);
const uploadImg = document.getElementById("uploadImg");
uploadImg.addEventListener("click", (() => thumbnail.click()));
const deleteImg = document.getElementById("deleteimg");
deleteImg.addEventListener("click", (() => {
    document.getElementById("thumbnail-preview").style.display = "none", document.getElementById("uploadImg").style.display = "block", document.getElementById("svgicon").style.display = "block", deleteImg.style.display = "none", reUploadImg.style.display = "none"
}));
const reUploadImg = document.getElementById("reUploadImg");

function uploadThumbnail(e) {
    const t = e.currentTarget.files[0];
    console.log(typeof t, t);
    var n = new FormData;
    n.append("image", t), $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/s3/image",
        data: n,
        mimeType: "multipart/form-data",
        processData: !1,
        contentType: !1,
        cache: !1,
        timeout: 6e5,
        success: function (e) {
            console.log(e), document.querySelector(".thumbnail-preview").setAttribute("src", e), thumbnailChange(), document.getElementById("thumbnail").value = e
        },
        error: function (e) {
            console.log(e)
        }
    })
}

reUploadImg.addEventListener("click", (() => thumbnail.click()));
var menu = ["Slide 1", "Slide 2", "Slide 3"], mySwiper = new Swiper(".swiper-container", {
    allowTouchMove: !1,
    simulateTouch: !1,
    speed: 500,
    scrollbar: {container: ".swiper-scrollbar", draggable: !1},
    navigation: {nextEl: ".next", prevEl: ".prev"},
    slidesPerView: "auto",
    centeredSlides: !0,
    spaceBetween: 200
});
document.getElementById("subcontent").addEventListener("keyup", (() => {
    textAreaCheck()
}));
const title = document.querySelector("title").text;

function thumbnailChange() {
    document.getElementById("svgicon").style.display = "none", document.getElementById("uploadImg").style.display = "none", document.getElementById("thumbnail-preview").style.display = "block", deleteImg.style.display = "block", reUploadImg.style.display = "block"
}

function textAreaCheck() {
    let e = document.getElementById("subcontent").value, t = document.querySelector(".textcount"),
        n = document.querySelector(".totaltext");
    0 == e.length || null == e ? t.textContent = "0" : t.textContent = e.length, e.length >= 150 ? (document.getElementById("subcontent").value = e.substring(0, 150), t.textContent = "150", t.style.color = "red", n.style.color = "red") : (t.style.color = "#070809", n.style.color = "#070809")
}

"수정" === title && (textAreaCheck(), isImgExist = document.getElementById("thumbnail-preview").getAttribute("src"), null == isImgExist || "" === isImgExist.trim() ? (thumbnailChange(), document.getElementById("thumbnail-preview").setAttribute("src", "/img/panda.png")) : thumbnailChange());
var input = document.querySelector('input[name="tags"]'), whitelist = ["C", "C++", "C#", "JAVA", "FrontEnd", "BackEnd"],
    tagify = new Tagify(input, {
        whitelist: whitelist,
        maxTags: 10,
        dropdown: {maxItems: 20, classname: "tags-look", enabled: 0, closeOnSelect: !1}
    });