<?php 
	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['name']) && 
		   isset($_POST['email']) && 
		   isset($_POST['password']) && 
		   isset($_POST['contact_no']) && 
		   isset($_POST['dob']) && 
		   isset($_POST['gender']) && 
		   isset($_POST['bloodgrp'])){

			$name = $_POST['name'];
			$email = $_POST['email'];
			$password = md5($_POST['password']);
			$contact = $_POST['contact_no'];
			$dob = $_POST['dob'];
			$gender = $_POST['gender'];
			$bloodgrp = $_POST['bloodgrp'];

			$query = "SELECT * FROM `user_db` WHERE BINARY `email`='$email' AND `password`='$password';";
			$result = mysqli_query($conn, $query);	

			if (mysqli_num_rows($result) == 1){
				$contactQuery = "SELECT * FROM `user_db` WHERE BINARY `contact_no`='$contact' AND `email`!='$email';";
				
				if(mysqli_num_rows(mysqli_query($conn, $contactQuery)) == 0){
					$updateQuery = "UPDATE `user_db` SET `name` = '$name', `contact_no` = '$contact', `dob` = '$dob', `gender` = '$gender', `bloodgrp` = '$bloodgrp' WHERE BINARY `user_db`.`email` = '$email';";

					mysqli_query($conn, $updateQuery);
					if(mysqli_affected_rows($conn)){
						$response['error'] = false;
						$response['message'] = "Profile Updated";
					} else {
						$response['error'] = true;
						$response['message'] = "Update not Successful";
					}
				} else {
					$response['error'] = true;
					$response['message'] = "Contact Number is Already Registered";
				}
			} else {
				$response['error'] = true;
				$response['message'] = "Some Error Occur, Try Again";
			}
		} else {
			$response['error'] = true;
			$response['message'] = "Required fields are missing";
		}
	} else {
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}

	echo json_encode($response);
?>