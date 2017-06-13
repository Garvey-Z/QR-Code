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

$account = $_POST['account'];
$nickname = $_POST['nickname'];
$gender = $_POST['gender'];
$phone = $_POST['phone'];

$sql = "UPDATE user SET nickname = '{$nickname}',gender = '{$gender}', phone = '{$phone}'
 where account = '{$account}'";
$stmt = $conn->prepare($sql);
echo $sql;

$stmt->execute();

$stmt->close();
$conn->close();
?>