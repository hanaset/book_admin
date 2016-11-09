<?php
	header("content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130","root","wjdqls56","book_admin");
	if($conn->connect_error){
		die("Connection failed: ".$conn->connect_error);
	}

	$id = $_POST['id'];
	$book_name = $_POST['book_name'];
	$num = $_POST['num'];

	$sql = "SELECT *FROM ".$id." WHERE book_name = '".$book_name."' && num = ".$num;
	$result = $conn->query($sql);

	$text = array();

	if($result->num_rows > 0)
	{
		while($row = $result->fetch_assoc()){
			array_push($text, array('author'=>$row["author"],'publisher'=>$row["publisher"],'name'=>$row["name"],'phone'=>$row['phone'],'loan_date'=>$row["loan_date"],'return_date'=>$row["return_date"],'image'=>$row['image']));

			break;
		}
	}
	
	$json = json_encode(array("result"=>$text));

	echo $json;

	$conn->close();
?>