<?php 
	require 'connection.php';
	require 'function/getCity.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['email']) &&
			isset($_POST['password']) &&
			isset($_POST['latlag'])){
			
			$email = $_POST['email'];
			$password = md5($_POST['password']);
			$latlag = $_POST['latlag'];
			
			$n = sscanf($latlag,"lat/lng: (%f,%f)", $lat, $lag);
			
			$city = NULL;
			while($city == NULL)
				$city = getCity($lat, $lag);
			
			$query = "SELECT * FROM `user_db` WHERE BINARY `email` LIKE '$email' AND `password` LIKE '$password';";
			$result = mysqli_query($conn, $query);	

			if (mysqli_num_rows($result) > 0){
				$updateQuery = "UPDATE `user_db` SET `city` = '$city', `latlag` = '$latlag' WHERE `user_db`.`email` = '$email';";
				mysqli_query($conn, $updateQuery);
				if(mysqli_affected_rows($conn)){
					$response['error'] = false;
					$response['message'] = "Location Updated";
				} else {
					$response['error'] = true;
					$response['city'] = $city;
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