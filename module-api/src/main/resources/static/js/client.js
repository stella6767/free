
// https://kentcdodds.com/blog/replace-axios-with-a-simple-custom-fetch-wrapper

console.log("setup custom client");

(function () {
    const THEME_KEY = "theme";

    // 1. 저장된 테마 적용 (페이지 로드 시)
    const savedTheme = localStorage.getItem(THEME_KEY) || "light";
    console.log(`Saved theme: ${savedTheme}`);
    document.documentElement.setAttribute("data-theme", savedTheme);

    // 2. 토글 상태 동기화
    const toggle = document.getElementById("themeToggle");

    if (toggle) {
        toggle.checked = savedTheme === "dark";

        toggle.addEventListener("change", () => {
            const theme = toggle.checked ? "dark" : "light";
            document.documentElement.setAttribute("data-theme", theme);
            localStorage.setItem(THEME_KEY, theme);
        });
    }
})();

function client(endpoint, {body, ...customConfig} = {}) {
    //const headers = {'Content-Type': 'application/json'}

    const config = {
        method: body ? 'POST' : 'GET',
        ...customConfig,
        headers: {
            ...customConfig.headers,
        },
    }

    if (body) {
        config.body = body;
        console.log("body", body);
    }

    return fetch(`${endpoint}`, config)
            .then(async response => {
            if (response.ok) {
                const data = await response
                return data.text();
            } else {
                const errorMessage = await response.text()
                return Promise.reject(new Error(errorMessage))
            }
        })
}

document.addEventListener('DOMContentLoaded', function () {
    const elements = document.querySelectorAll('[onload]');
    elements.forEach(function (element) {
        const onload = element.getAttribute('onload');
        if (onload) {
            new Function('element', onload).call(element, element);
        }
    });
});


htmx.defineExtension('hx-dataset-include', {
    encodeParameters: function (xhr, parameters, elt) {
        Object
            .keys(elt.dataset)
            .forEach(k => parameters
                .append(k, elt.dataset[k]))
    }
})


// htmx.config.responseHandling = [
//     {code: "204", swap: false},
//     {code: "[23]..", swap: true},
//     {code: "[45]..", swap: true, error: true},
//     {code: "...", swap: false}
// ]



// document.addEventListener('htmx:afterRequest', e => {
//     if (!e.detail.xhr.status.toString().startsWith('2')) {
//         let errorBanner = document.getElementById("toast");
//         errorBanner.innerHTML = e.detail.xhr.responseText;
//         errorBanner.style.display = 'block';
//     }
// });


// document.addEventListener('htmx:configRequest', function(evt) {
//     //evt.detail.headers['Authentication'] = "전달자: " + getJWT()
//     evt.detail.headers['testHeader'] = "test"
// });


// document.body.addEventListener('htmx:afterRequest', function (evt) {
//     const errorTarget = document.getElementById("htmx-alert")
//     if (evt.detail.successful) {
//         // Successful request, clear out alert
//         errorTarget.setAttribute("hidden", "true")
//         errorTarget.innerText = "";
//     } else if (evt.detail.failed && evt.detail.xhr) {
//         // Server error with response contents, equivalent to htmx:responseError
//         console.warn("Server error", evt.detail)
//         const xhr = evt.detail.xhr;
//         errorTarget.innerText = `Unexpected server error: ${xhr.status} - ${xhr.statusText}`;
//         errorTarget.removeAttribute("hidden");
//     } else {
//         // Unspecified failure, usually caused by network error
//         console.error("Unexpected htmx error", evt.detail)
//         errorTarget.innerText = "Unexpected error, check your connection and try to refresh the page.";
//         errorTarget.removeAttribute("hidden");
//     }
// });