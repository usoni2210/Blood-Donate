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
	
			$unique_qry = "SELECT * FROM `user_db` WHERE `email` LIKE '$email';";
			$result = mysqli_num_rows(mysqli_query($conn, $unique_qry));
			$insert_qry = "INSERT INTO `user_db` (`id`, `name`, `email`, `password`, `contact_no`, `dob`, `gender`, `bloodgrp`, `city`, `latlag`, `isdonor`) VALUES (NULL, '$name', '$email', '$password', '$contact', '$dob', '$gender', '$bloodgrp', NULL, NULL, '0')";	
			
			if ($result > 0) {
				$response['error'] = true;
				$response['message'] = "User Name is Already Registered"; 
			} elseif (mysqli_query($conn, $insert_qry)){
				$response['error'] = false;
				$response['message'] = "User Registered Successfully";
			} else {
				$response['error'] = true;
				$response['message'] = "Error\nSome Problem Detect, Try Again Later"; 
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