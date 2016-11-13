<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$mode = $_POST['mode'];
	$content = $_POST['content'];

	$mode_content;


	if($mode == 1){
		$mode_content = "passwd";
	}else if($mode == 2){
		$mode_content = "factory";
	}

	$sql = "UPDATE user SET ".$mode_content." = '".$content."' WHERE id = '".$id."'";

	$result = $conn->query($sql);

	if($result)
		echo "1";
	else
		echo $conn->error;

	$conn->close();
?>