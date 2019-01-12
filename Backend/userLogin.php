<?php 
	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['email']) && isset($_POST['password'])){
			$email = $_POST['email'];
			$password = md5($_POST['password']);

			$query = "SELECT * FROM `user_db` WHERE BINARY `email`='$email' AND `password`='$password';";
			$result = mysqli_query($conn, $query);	

			if (mysqli_num_rows($result) > 0){
				$user = mysqli_fetch_assoc($result);	
				$response['error'] = false;
				$response['message'] = "Login Successfully";
				//$response['image'] = $user['image'];
				$response['name'] = $user['name'];
				$response['email'] = $user['email'];
				$response['password'] = $user['password'];
				$response['contact_no'] = $user['contact_no'];
				$response['dob'] = $user['dob'];
				$response['gender'] = $user['gender'];
				$response['bloodgrp'] = $user['bloodgrp'];
				$response['isdonor'] = $user['isdonor'] == 1;
			} else {
				$response['error'] = true;
				$response['message'] = "Invalid Username or Password";
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