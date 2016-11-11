<?php
	header("Content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130", "root", "wjdqls56", "book_admin");
// Check connection
	if ($conn->connect_error) {
   		die("Connection failed: " . $conn->connect_error);
	} 

	$id = $_POST['id'];
	$content = $_POST['content'];
	$kind = $_POST['kind'];

	if(!strcmp("책 이름",$kind)){
		$sql = "SELECT * FROM ".$id." WHERE book_name like '%".$content."%'";
	}else if(!strcmp("저자",$kind)){
		$sql = "SELECT * FROM ".$id." WHERE author like '%".$content."%'";
	}else if(!strcmp("출판사",$kind)){
		$sql = "SELECT * FROM ".$id." WHERE publisher like '%".$content."%'";
	}else if(!strcmp("대출자",$kind)){
		$sql = "SELECT * FROM ".$id." WHERE name like '%".$content."%'";
	}

	$result = $conn->query($sql);

	if($reuslt == false)
		echo $conn->error;

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