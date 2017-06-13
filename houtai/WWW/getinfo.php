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
/*
$iden = $_POST['identity'];
$number = $_POST['number'];*/

$iden = $_REQUEST['identity'];
$number = $_REQUEST['number'];
// $iden = "customer";
// $number = 217;

$sql = "SELECT code.info FROM code,connect where code.number = connect.cid 
        and connect.uiden = '{$iden}' and code.number = {$number} limit 1";
$result = $conn->query($sql);
echo $sql;

if ($result->num_rows > 0) {
    // 输出每行数据
    while($row = $result->fetch_assoc()) {
        $sql2 = $row['info'];
        $res = $conn->query($sql2);
        if ($res->num_rows > 0){
             while($rew = $res->fetch_assoc()) {
                    $cname = $rew['name'];
                    $cphoto = $rew['photo'];
                    $csize = $rew['size'];
                    $cprice = $rew['price'];

                    if ($iden == "customer"){
                        $cnumber = 'NULL';
                    }else{
                        $cnumber = $rew['number'];
                    }
                    if ($iden == "administrator"){
                        $cprime_cost = $rew['prime_cost'];
                        $id = $rew['id'];
                        $pop = $rew['place_of_production'];
                    }else {
                        $cprime_cost = 'NULL';
                        $id = 'NULL';
                        $pop = 'NULL';
                    }

                    $url = "clothes.html?name={$cname}&photo={$cphoto}&size={$csize}&price={$cprice}&number={$cnumber}&prime_cost={$cprime_cost}&id={$id}&place_of_production={$pop}";
                    Header("HTTP/1.1 303 See Other"); 
                    Header("Location: $url"); 
             }
        }
        else
        {
            echo "YCCCC";
        }
    }
} else {
    echo "You cant";
}

/*
Header("HTTP/1.1 303 See Other"); 
Header("Location: $url"); 
*/

/*
if ($result->num_rows > 0) {
    // 输出每行数据
    while($row = $result->fetch_assoc()) {
    	$type = $row['type'];
        $filename = $row["info"];
        switch ($type) {
        	case 1:
        		echo $filename;
        		break;
        	case 2:
				echo "<img src='showpictest.php?filename={$filename}'>";
        		break;
        	case 3:
				exit;
        		break;
        	default:
        		break;
        }
		echo "<br>";
    }
} else {
    echo "You cant";
}

*/
$conn->close();
?>