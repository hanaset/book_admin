<?php
    header("Content-Type: text/html;charset=UTF-8");

    $conn = new mysqli("114.70.93.130","root","wjdqls56","book_admin");

    if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    } 

    $id = $_POST['id'];
    $passwd = $_POST['passwd'];
    $factory = $_POST['factory'];

    $sql = "INSERT INTO user(id,passwd,factory) VALUES ('".$id."','".$passwd."','".$factory."')";
    $result = $conn->query($sql);

    $sql1 = "CREATE TABLE ".$id."(
    book_name VARCHAR(50),
    ISBN VARCHAR(30),
    author VARCHAR(30),
    publisher VARCHAR(30),
    content VARCHAR(250),
    loan_date DATE,
    return_date DATE,
    num INT(6),
    loan INT(6),
    loan_call INT(6),
    return_call INT(6),
    phone VARCHAR(20),
    image VARCHAR(300)
    )";

    $result1 = $conn->query($sql1);
     
    if($result && $result1)
      echo "1";
    else
      echo "-1";

    $conn->close();
?>