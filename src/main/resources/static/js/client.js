
// https://kentcdodds.com/blog/replace-axios-with-a-simple-custom-fetch-wrapper

console.log("setup custom client");

function client(endpoint, {body, ...customConfig} = {}) {
    const headers = {'Content-Type': 'application/json'}
    const config = {
        method: body ? 'POST' : 'GET',
        ...customConfig,
        headers: {
            ...headers,
            ...customConfig.headers,
        },
    }

    if (body) {
        config.body = JSON.stringify(body)
    }

    return window
        .fetch(`/${endpoint}`, config)
        .then(async response => {
            if (response.ok) {
                return await response.json()
            } else {
                const errorMessage = await response.text()
                return Promise.reject(new Error(errorMessage))
            }
        })
}