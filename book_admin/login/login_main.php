<?php
	header("content-Type: text/html;charset=UTF-8");

	$conn = new mysqli("114.70.93.130","root","wjdqls56","book_admin");
	if($conn->connect_error){
		die("Connection failed: ".$conn->connect_error);
	}

	$id = $_POST['id'];
	$loan = 0;
	$loan_call = 0;
	$return_call = 0;

	$sql_loan = "SELECT *FROM ".$id." WHERE loan > 0";
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

	$sql_loan_call = "SELECT *FROM ".$id." WHERE loan_call > 0";
	$result_loan_call = $conn->query($sql_loan_call);

	if($result_loan_call->num_rows > 0)
	{
		while($row = $result_loan_call->fetch_assoc())
		{
			$loan_call++;
		}

		echo $loan_call;
	}
	else
		echo "-1";

	echo ",";

	$sql_return_call = "SELECT *FROM ".$id." WHERE return_call > 0";
	$result_return_call = $conn->query($sql_return_call);

	if($result_return_call->num_rows > 0){
		while($row = $result_return_call->fetch_assoc()){
			$return_call++;
		}

		echo $return_call;
	}
	else
		echo "-1";

	$conn->close();
?>