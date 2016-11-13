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
	$name = $_POST['name'];
	$phone = $_POST['phone'];
	$loan_day = date("Y-m-d", mktime(0,0,0,date('m'),date('d'),date('Y')));
	$return_day = date("Y-m-d", mktime(0,0,0,date('m'),date('d')+14,date('Y')));


	$sql = "UPDATE ".$id." SET state_num = 1, loan_date = '".$loan_day."', return_date = '".$return_day."',name = '".$name."', phone = '".$phone."' WHERE num =".$num." && ISBN = '".$ISBN."'";
	$result = $conn->query($sql);

	if($result)
		echo "1";
	else
		echo $conn->error;

	$conn->close();
?>