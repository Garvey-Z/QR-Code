<?php
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "qrcode";
 
// 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);
 
// 检测连接
if ($conn->connect_error) {
    die("Link is over: " . $conn->connect_error);
} 

$account = $_REQUEST['account'];

$sql = "SELECT password,identity FROM user where account = {$account} ";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // 输出每行数据
    while($row = $result->fetch_assoc()) {
        echo json_encode($row);
    }
} else {
    echo "0 个结果";
}

// $stmt->close();
$conn->close();
?>