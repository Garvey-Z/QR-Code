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

$sql = "SELECT * FROM user where account = '{$account}' ";
$result = $conn->query($sql);

$results = array();
if ($result->num_rows > 0) {

	$i=0;
    while($row = $result->fetch_assoc()) {
		$results[$i] = $row;
		$i++;
    }
    echo json_encode($results);

} else {
    echo "0 个结果";
}

// $stmt->close();
$conn->close();
?>