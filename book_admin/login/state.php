<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
   		die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$state = $_POST['ISBN'];

	if($state == 0)
		$sql = "SELECT * FROM ".$id."WHERE state_num > ".$state;
	else
		$sql = "SELECT * FROM ".$id."WHERE state_num = ".$state;
	$result = $conn->query($sql);

	$text = array();

	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			array_push($text, array('book_name'=>$row["book_name"],'num'=>$row["num"], 'name'=>$row["name"], 'loan_date'=>$row["loan_date"], 'return_date'=>$row["return_date"], 'state_num'=>$row["state_num"]));
		}
	

	 $json = json_encode(array("result"=>$text));

	 echo $json;
	}else{
		echo "-1";
	}

	$conn->close();
?>