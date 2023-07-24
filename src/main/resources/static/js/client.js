
// https://kentcdodds.com/blog/replace-axios-with-a-simple-custom-fetch-wrapper

console.log("setup custom client");

function client(endpoint, {body, ...customConfig} = {}) {
    //const headers = {'Content-Type': 'application/json'}

    //console.log("customconfig", ...customConfig)

    const config = {
        method: body ? 'POST' : 'GET',
        ...customConfig,
        headers: {
            ...customConfig.headers,
        },
    }

    if (body) {
        config.body = body
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