<?php
	header("content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130","root","wjdqls56","book_admin");

	if($conn->connect_error){
		die("Connection failed: ".$conn->connect_error);
	}

	$id = $_POST['id'];
	$book_name = $_POST['book_name'];
	$ISBN = $_POST['ISBN'];
	$author = $_POST['author'];
	$publisher = $_POST['publisher'];
	$content = $_POST['content'];
	$img = $_POST['img'];

	$num = 1;

	$sql = "SELECT *FROM ".$id." WHERE ISBN = ".$ISBN;
	$result = $conn->query($sql);

	if($result->num_rows > 0)
	{
		while($row = $result->fetch_assoc()){
			$num++;
		}
	}

	$sql = "INSERT INTO ".$id."(book_name, ISBN, author, publisher, content, num, image) VALUES ('".$book_name."','".$ISBN."','".$author."','".$publisher."','".$content."','".$num."','".$img."')";

	$result = $conn->query($sql);

	if($result)
		echo "1";
	else
		echo "-1";


	$conn->close();

?>