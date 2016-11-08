<?php
	header("content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
	if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$passwd = $_POST['passwd'];

	$sql = "SELECT * FROM user WHERE id ='".$id."'";
	$result = $conn->query($sql);

	if($result->num_rows > 0)
	{
		$row = $result->fetch_assoc();

		if(strcmp($row["passwd"], $passwd) == 0)
			echo $row["factory"];
		else
			echo "-1";
	}
	else
		echo "-1";

	$conn->close();
?>