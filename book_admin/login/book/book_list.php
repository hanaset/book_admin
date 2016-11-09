<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
   		die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];

	$sql = "SELECT * FROM ".$id;
	$result = $conn->query($sql);

	$text = array();

	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			array_push($text, array('book_name'=>$row["book_name"],'author'=>$row["author"], 'publisher'=>$row["publisher"], 'state_num'=>$row["state_num"], 'num'=>$row["num"]));
		}
	}

	 $json = json_encode(array("result"=>$text));

	 echo $json;

	$conn->close();
?>