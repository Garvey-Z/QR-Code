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

$file_name = $_GET['filename'];

$filename = $file_name;
$size = getimagesize($filename);
$fp = fopen($filename, "rb");

if ($size && $fp) {
    header("Content-type: {$size['mime']}");
    fpassthru($fp);
} else {
    // error
}

$conn->close();
?>