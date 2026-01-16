function updateFilePreview(event) {
    const input = event.target;
    const file = input.files && input.files[0];
    let maxFileSize = 100 * 1024 * 1024;

    const fileNameSpan = document.getElementById("file-name");
    const previewContainer = document.getElementById("image-preview");


    if (!file) {
        fileNameSpan.textContent = "선택된 파일 없음";
        previewContainer.innerHTML = '<span class="text-gray-500">프로필 이미지</span>';
        return;
    }

    if (file.size > maxFileSize) {
        alert("파일 용량은 100MB 이하만 가능합니다.");
        input.value = "";
        return;
    }
    const allowed = ["image/jpeg", "image/png", "image/bmp"];
    if (!allowed.includes(file.type)) {
        alert("jpg, png, bmp 파일만 가능합니다.");
        input.value = "";
        return;
    }


    // 1️⃣ 파일명 표시
    fileNameSpan.textContent = file.name;

    // 2️⃣ 이미지 파일인지 확인
    if (!file.type.startsWith("image/")) {
        alert("이미지 파일만 업로드 가능합니다.");
        input.value = "";
        return;
    }

    // 3️⃣ FileReader로 미리보기 생성
    const reader = new FileReader();
    reader.onload = function (e) {
        previewContainer.innerHTML = `
            <img class="w-full h-full object-cover rounded-full" src="${e.target.result}">
        `;
    };

    reader.readAsDataURL(file);
}




function moveFocus(e, el, nextId) {
    el.value = el.value.replace(/[^0-9]/g, '');
    if (el.value && nextId) {
        document.getElementById(nextId).focus();
    }
}

function collectAndSubmitCode(form) {
    const code = Array.from({ length: 6 }, (_, i) => {
        return document.getElementById(`code${i + 1}`).value;
    }).join('');

    // code가 6자리일 때만 submit
    if (code.length === 6) {
        document.getElementById('codeFull').value = code;
        form.dispatchEvent(new Event("verify-code", { bubbles: true }));
    }
}

function initInputField(){
    console.log("initInputField");
    document.querySelectorAll('#main-input-form input')
        .forEach(input => input.value = '');
}


let providerElement = document.getElementById("provider");


if (providerElement){
    providerElement.addEventListener("change", e => {
        const type = e.target.value
        document.getElementById("endpoint").classList.toggle("hidden", type !== "CloudFlare")
        document.getElementById("region-form").classList.toggle("hidden", type !== "AWS")
    })
}




