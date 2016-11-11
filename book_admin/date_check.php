<?php
	header("Content-Type: text/html;charset=UTF-8");

    $conn = new mysqli("114.70.93.130","root","wjdqls56","book_admin");

    if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    }

    $id = $_POST['id'];
    $todate = date("Y-m-d", mktime(0,0,0,date('m'),date('d'),date('Y')) );
    $today = strtotime($todate); 

    $sql1 = "SELECT *FROM ".$id."WHERE return_date != 'NULL'";
    $result = $conn->query($sql1);

    if($result->num_rows > 0){
    	while($row = $result->fetch_assoc()){

    		$date = strtotime($row["return_date"]);

    		if($today > $date){

    			$sql2 = "UPDATE ".$id." SET state_num = 4 WHERE num = ".$row["num"]." && book_name = '".$row["book_name"]."'";

    		
    			if($conn->query($sql2) == false)
    				echo $conn->error;

    			
    		}
    	}
    }
    else{
    	echo "-1";
    }

    $conn->close();
?>