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

$table = "cloth_info";

$name = $_POST['name'];
$size = $_POST['size'];
$price = $_POST['price'];

$prime_cost = $_POST['prime_cost'];
$number = $_POST['number'];
$place_of_production = $_POST['pop'];
$codenumber = $_POST['codenumber'];

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

$base_path = "./clothes/"; //存放目录
$target_path = $base_path . basename ( $_FILES ['userfile'] ['name'] );
$photo = $target_path;

//cloth_info
$sql = "INSERT INTO cloth_info (name,photo,size,price,prime_cost,number,place_of_production) 
	VALUES ('{$name}','{$photo}','{$size}','{$price}','{$prime_cost}','{$number}','{$place_of_production}' )";
echo $sql;


//code------
$temp = "SELECT * from cloth_info where name = '{$name}' ";
$sql2 =$conn->prepare( "INSERT INTO code (info,type,number) VALUES (?,3,{$codenumber})");
$sql2->bind_param("s",$temp);
$sql2->execute();

//权限
$stmt = $conn->prepare("INSERT into connect(uiden,cid,nextid) values (?,'{$codenumber}',?)");
$useriden = array(array("customer",1,2,3,4),array("salesman",1,2,3,4,6),array("administrator",0,1,2,3,4,5,6,7));
for ($row = 0; $row < 3; $row++) {
	$uiden = $useriden[$row][0];
  for ($col = 1; $col < count($useriden[$row]); $col++) {
    $nextid = $useriden[$row][$col];
    $stmt->bind_param("si", $uiden, $nextid);
	$stmt->execute();
  }
}
$stmt->close();

if (move_uploaded_file ( $_FILES ['userfile'] ['tmp_name'], $target_path )) {
  $array = array (
      "status" => true,
      "msg" => $_FILES ['userfile'] ['name'] 
  );
	$conn->query($sql);
  echo json_encode ( $array );

} else {
  $array = array (
      "status" => false,
      "msg" => "There was an error uploading the file, please try again!" . $_FILES ['userfile'] ['error'] 
  );
  echo json_encode ( $array );
}

?>