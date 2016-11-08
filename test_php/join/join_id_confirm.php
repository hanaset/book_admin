<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
	} 

	$data = $_POST['id'];

	$sql = "SELECT * FROM user WHERE id ='".$data."'";
	$result = $conn->query($sql);

	if($result->num_rows > 0){
		echo "-1";
	}
	else
		echo "1";

	$conn->close();
?>