<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$book_name = $_POST['book_name'];
	$num = $_POST['num'];
	$state_num = $_POST['state_num'];

	$modity_num = 0;

	if($state_num == 2){
		$modity_num = 1;
	}else if($state_num == 3){
		$modity_num = 0;
	}

	$sql = "UPDATE ".$id." SET state_num = ".$modity_num." WHERE num =".$num." && book_name = '".$book_name."'";
	$result = $conn->query($sql);

	if($result)
		echo "1";
	else
		echo $conn->error;

	$conn->close();
?>