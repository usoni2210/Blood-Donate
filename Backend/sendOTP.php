<?php
	require 'connection.php';
	
	//otp generate and send to data base with gmail
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
		if(isset($_POST['email'])) {

			$email=$_POST['email'];
			$result = mysqli_query($conn,"SELECT * FROM user_db WHERE email='$email'");
			$count  = mysqli_num_rows($result);

			if($count>0) {
				// generate OTP
				$otp = rand(100000,999999);
				$txt="Dear customer, please use below verification code to Reset your Password of Blood Donate \n
						-- Valid for 15 minutes --\n OTP Code : $otp \n\n Regards, BloodDonate  \n \n \n \n 
						This is an auto-generated email.  Do not reply to this email.";
				// Send OTP
				$insert = "INSERT INTO otp_db(email,otp) VALUES ('$email','$otp')";	
				if(mysqli_query($conn,$insert)) {
					$header="Blood Donate - Password Reset Verification";				
					$status= mail($email, $header, $txt);

					if($status){
						$response['error'] = false;
						$response['message'] = "OTP Send to your Email ID";
					} else {
						$response['error'] = true;
						$response['message'] = "OTP Can't Send";	
					}
				} else {
					$response['error'] = true;
					$response['message'] = "Unable To generate OTP";
				}
			} else {
				$response['error'] = true;
				$response['message'] = "Email Doesn't Exists"; 
			}
		} else {
			$response['error'] = true;
			$response['message'] = "Required Fields are Missing"; 
		}
	}else {
		$response['error'] = true;
		$response['message'] = "Invalid Request"; 
	}//end of server if
	
	echo json_encode($response);
?>