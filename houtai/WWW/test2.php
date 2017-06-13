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
$password = $_POST['password'];
$nickname = $_POST['nickname'];
$gender = $_POST['gender'];
$phone = $_POST['phone'];

$stmt = $conn->prepare("INSERT INTO user (account,nickname,gender,password,phone) VALUES (?,?,?,?,?)");
$stmt->bind_param("sssss", $account, $nickname, $gender, $password, $phone);
$stmt->execute();

$stmt->close();
$conn->close();
?>