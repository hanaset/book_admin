<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$num = $_POST['num'];
	$ISBN = $_POST['ISBN'];

	$sql = "UPDATE ".$id." SET state_num = 0 WHERE num =".$num." && ISBN = '".$ISBN."'";
	$result = $conn->query($sql);

	if($result)
		echo "1";
	else
		echo $conn->error;

	$conn->close();
?>