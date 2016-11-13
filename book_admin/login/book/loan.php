<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
   		die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$ISBN = $_POST['ISBN'];

	$sql = "SELECT * FROM ".$id."WHERE ISBN = '".$ISBN."' && state_num = 0";
	$result = $conn->query($sql);

	$text = array();

	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			array_push($text, array('book_name'=>$row["book_name"],'name'=>$row["name"], 'num'=>$row["num"], 'image'=>$row["image"], 'phone'=>$row["phone"]));

			break;
		}

	$json = json_encode(array("result"=>$text));

	echo $json;
	}else{
		echo "-1";
	}

	 

	$conn->close();
?>