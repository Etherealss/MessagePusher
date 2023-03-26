/**
 * @fileoverview
 * @author  wtk
 * @date 2023-03-21
 */
const requestOptions = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
};

fetch('/api/data', requestOptions)
    .then(response => {
        // 处理响应数据
        console.log(response.status);  // 状态码
        return response.json();  // 将响应数据解析为 JSON 格式
    })
    .then(data => {
        // 处理 JSON 数据
        console.log(data);
    })
    .catch(error => {
        // 处理错误
        console.error(error);
    });
