/**
 * @fileoverview
 * @author  wtk
 * @date 2023-03-21
 */
// 创建 WebSocket
const socket = new WebSocket('ws://localhost:8080');

// 监听连接事件
socket.addEventListener('open', (event) => {
    console.log('WebSocket connected.');
});

// 监听消息事件
socket.addEventListener('message', (event) => {
    console.log('Received message:', event.data);

    // 将消息显示在文本框中
    const messageBox = document.getElementById('message-box');
    messageBox.innerHTML += event.data + '<br/>';
});

// 监听关闭事件
socket.addEventListener('close', (event) => {
    console.log('WebSocket disconnected.');
});
