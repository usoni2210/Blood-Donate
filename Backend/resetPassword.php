<?php

	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(isset($_POST['email']) && isset($_POST['otp']) && isset($_POST['password'])){
			$email = $_POST['email'];
			$password = md5($_POST['password']);
			$otp = $_POST["otp"];
			
			$result = mysqli_query($conn,"SELECT * FROM otp_db WHERE email='$email' AND otp='$otp' AND NOW()<=DATE_ADD(created_at, INTERVAL 15 MINUTE)");
			$count  = mysqli_num_rows($result);
			
			if($count>0) {
				$del = mysqli_query($conn,"DELETE FROM otp_db WHERE otp='$otp' AND email='$email'");

				// Now we are going to do Main thing that is Reset Password
				$update=" UPDATE user_db SET password='$password' WHERE email ='$email' ";
				if(mysqli_query($conn,$update)){
					$response['error'] = false;
					$response['message'] = "Password Successfully Changed";
				} else {
					$response['error'] = true;
					$response['message'] = "Something went Wrong";
				}
			} else {
				$response['error'] = true;
				$response['message'] = "Invalid OTP";
			}
		} else {
			$response['error'] = true;
			$response['message'] = "Required Fields are Missing";
		}
	} else {
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}
				
	echo json_encode($response);
?>