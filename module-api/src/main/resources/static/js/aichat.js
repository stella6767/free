// const chatMessages = document.getElementById('chat-messages');
// const userInput = document.getElementById('user-input');
//
// userInput.addEventListener('keydown', (e) => {
//     if (e.key === 'Enter' && !e.altKey) {
//         e.preventDefault(); // 줄바꿈 방지
//         sendMessage();
//     }
//     //if (e.key === 'Enter') sendMessage();
// });
//
// function sendMessage() {
//     const message = userInput.value.trim();
//     if (!message) return;
//
//     addMessage(message, 'user');
//     setTimeout(() => addMessage('네, 이해했습니다. 이에 대한 답변을 준비 중입니다...', 'bot'), 800);
//     userInput.value = '';
// }
//
// function addMessage(text, sender) {
//     const messageDiv =
//         document.createElement('div');
//     messageDiv.className = `p-3 rounded-lg max-w-xs ${sender === 'user' ? 'ml-auto bg-[#414158] text-white' : 'bg-[#525252] border'}`;
//     messageDiv.textContent = text;
//     chatMessages.appendChild(messageDiv);
//     chatMessages.scrollTop = chatMessages.scrollHeight;
// }
