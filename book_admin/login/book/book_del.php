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

	$sql = "DELETE FROM ".$id." WHERE book_name = '".$book_name."' && num = ".$num;

	if($conn->query($sql) == True){
		echo "1";
	}else{
		echo $conn->error;
	}


	$conn->close();
?>