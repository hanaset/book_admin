<?php
	header("content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130","root","wjdqls56","book_admin");
	if($conn->connect_error){
		die("Connection failed: ".$conn->connect_error);
	}

	$id = $_POST['id'];
	$loan = 0;
	$late_call = 0;

	$sql_loan = "SELECT *FROM ".$id." WHERE state_num = 1";
	$result_loan = $conn->query($sql_loan);

	if($result_loan->num_rows > 0)
	{
		while($row = $result_loan->fetch_assoc()){
			$loan++;
		}
		echo $loan;
	}
	else
		echo "-1";

	echo ",";

	$sql_loan_call = "SELECT *FROM ".$id." WHERE state_num = 2";
	$result_loan_call = $conn->query($sql_loan_call);

	if($result_loan_call->num_rows > 0)
	{
		while($row = $result_loan_call->fetch_assoc())
		{
			$late_call++;
		}

		echo $late_call;
	}
	else
		echo "-1";


	$conn->close();
?>