<?php
	require 'connection.php';
	require 'function/getCity.php';
	require 'function/getDirectDistance.php';
	
	$response = array();
	$donor = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['user_name']) &&
			isset($_POST['contact_no']) &&
			isset($_POST['bloodgrp']) &&
			isset($_POST['latlag']) &&
			isset($_POST['email'])){

			$username = $_POST['user_name'];
			$email = $_POST['email'];
			$contact = $_POST['contact_no'];
			$bloodgrp = $_POST['bloodgrp'];
			$latlag = $_POST['latlag'];

			sscanf($latlag,"lat/lng: (%f,%f)",$lat, $lag);
			$city = getCity($lat, $lag);
			
			$query = "SELECT * FROM `user_db` WHERE `bloodgrp` = '$bloodgrp' AND `city` = '$city' AND `isdonor` = 1 AND `email` != '$email'";
			$result = mysqli_query($conn, $query);

			if(mysqli_num_rows($result) > 0){
				while($row = mysqli_fetch_array($result)){
					sscanf($row['latlag'], "lat/lng: (%f,%f)",$lat2, $lag2);
					$distance = getDirectDistance($lat, $lag, $lat2, $lag2);
					
					if($distance <=5000){
						$data = array();
						$data['name'] = $row['name'];
						$data['dob'] = $row['dob'];
						$data['contact_no'] = $row['contact_no'];
						$data['gender'] = $row['gender'];
						$data['distance'] = $distance;
						array_push($donor, $data);
					}
				}

				if (!$result){
					$response['error'] = true;
					$response['message'] = "Some error occur, Try again";
				} else if(!count($donor)){
					$response['error'] = true;
					$response['message'] = "No Donor found";
				}else {
					$response['donor'] = $donor;
					$response['error'] = false;
					$response['message'] = "Donor Found";
				}
			} else {
				$response['error'] = true;
				$response['message'] = "No Donor found";
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