<?php 
	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['email']) && isset($_POST['password']) && isset($_POST['isdonor'])){
			$email = $_POST['email'];
			$password = md5($_POST['password']);

			$boolIsDonor = filter_var($_POST['isdonor'] , FILTER_VALIDATE_BOOLEAN );
			$isdonor = (int)$boolIsDonor;


			$query = "SELECT * FROM `user_db` WHERE BINARY `email` LIKE '$email' AND `password` LIKE '$password';";
			$result = mysqli_query($conn, $query);	

			if (mysqli_num_rows($result) > 0){
				$updateQuery = "UPDATE `user_db` SET `isdonor` = '$isdonor' WHERE `user_db`.`email` = '$email';";
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