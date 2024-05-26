
// https://kentcdodds.com/blog/replace-axios-with-a-simple-custom-fetch-wrapper

console.log("setup custom client");

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


// document.addEventListener('htmx:afterRequest', e => {
//     if (!e.detail.xhr.status.toString().startsWith('2')) {
//         let errorBanner = document.getElementById("toast");
//         errorBanner.innerHTML = e.detail.xhr.responseText;
//         errorBanner.style.display = 'block';
//     }
// });