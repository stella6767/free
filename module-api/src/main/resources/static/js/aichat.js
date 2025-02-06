
const clientId = generateUUID(); // 고유 UUID 생성

const eventSource =
    new EventSource(`http://localhost:8080/ai/chat-sse/${clientId}`);

eventSource.onmessage = (event) => {
    console.log("AI Response:", event.data);
};

eventSource.onerror = (error) => {
    console.error("SSE Error:", error);
    eventSource.close();
};


// 페이지를 떠날 때 연결 종료
window.addEventListener("beforeunload", () => {
    eventSource.close();
});

function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
