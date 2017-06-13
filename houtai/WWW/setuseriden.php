<?php
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "qrcode";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Link is over: " . $conn->connect_error);
}
$account = $_REQUEST['account'];
$iden = $_REQUEST['identity'];
$sql = "update user set identity = '{$iden}' where account = '{$account}'";
$conn->query($sql);

$conn->close();
?>