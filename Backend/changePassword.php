<?php 
	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['email']) && isset($_POST['password']) && isset($_POST['newpwd'])){
			$email = $_POST['email'];
			$password = md5($_POST['password']);
			$newPwd = md5($_POST['newpwd']);

			$query = "SELECT * FROM `user_db` WHERE BINARY `email`='$email' AND `password`='$password';";
			$result = mysqli_query($conn, $query);	

			if (mysqli_num_rows($result) == 1){
				$updateQuery = "UPDATE `user_db` SET `password` = '$newPwd' WHERE `user_db`.`email` = '$email';";
				mysqli_query($conn, $updateQuery);
				if(mysqli_affected_rows($conn)){
					$response['error'] = false;
					$response['message'] = "Password Updated";
				} else {
					$response['error'] = true;
					$response['message'] = "Update Failed";
				}
			} else {
				$response['error'] = true;
				$response['message'] = "Invalid Password";
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