<?php
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "qrcode";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Link is over: " . $conn->connect_error);
}
$sql = "SELECT account,nickname,identity FROM user where identity <> 'administrator'";
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
    echo "0 涓粨鏋?";
}

// $stmt->close();
$conn->close();
?>