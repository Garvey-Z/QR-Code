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

if ($_FILES['userfile']['error'] > 0)
{
	echo 'Problem:';
	switch ($_FILES['userfile']['error']) {
		case '1':
			echo "File exceeded upload_max_filesize";
			break;
		case '2': echo "File exceeded max_file_size";
			break;
		case '3': echo "File only partially uploaded";
			break;
		case '4': echo "No file uploaded";
			break;
		case '6': echo "Cannot uoload file: no temp directory specified";
			break;
		case '7': echo "upload failed: cannot write to disk";
			break;
	}
	exit;
}

$type=$_FILES['userfile']['type'];

$base_path = "./info/"; //存放目录
$target_path = $base_path . basename ( $_FILES ['userfile'] ['name'] );

// $stmt = $conn->prepare("INSERT INTO code (info,type) VALUES ('{$target_path}','{$type}')");	
// $stmt->execute();

if (move_uploaded_file ( $_FILES ['userfile'] ['tmp_name'], $target_path )) {
  $array = array (
      "status" => true,
      "msg" => $_FILES ['userfile'] ['name'] 
  );
  echo json_encode ( $array );

} else {
  $array = array (
      "status" => false,
      "msg" => "There was an error uploading the file, please try again!" . $_FILES ['userfile'] ['error'] 
  );
  echo json_encode ( $array );
}
?>