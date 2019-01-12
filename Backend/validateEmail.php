<?php 
	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['email'])){
			$email = $_POST['email'];

			$query = "SELECT * FROM `user_db` WHERE `email` LIKE '$email';";
			$result = mysqli_query($conn, $query);	

			if (mysqli_num_rows($result) > 0){
				$response['error'] = true;
				$response['message'] = "Email ID is Already Registered";
			} else {
				$response['error'] = false;
				$response['message'] = "Email ID not Registered";
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