
// https://kentcdodds.com/blog/replace-axios-with-a-simple-custom-fetch-wrapper

console.log("setup custom client");

function client(endpoint, method, {body, ...customConfig} = {}) {
    const headers = {'Content-Type': 'application/json'}
    const config = {
        method: method,
        ...customConfig,
        headers: {
            ...headers,
            ...customConfig.headers,
        },
    }

    // if (body) {
    //     config.body = JSON.stringify(body)
    // }
    return fetch(`${endpoint}`, config)
            .then(async response => {
            if (response.ok) {
                return await response.json()
            } else {
                const errorMessage = await response.text()
                return Promise.reject(new Error(errorMessage))
            }
        })
}