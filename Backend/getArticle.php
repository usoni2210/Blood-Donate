<?php 
	require 'connection.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$query = "SELECT * FROM `article_db`;";
		$rows = mysqli_num_rows(mysqli_query($conn, $query));

		if($rows > 0){
			$num = (rand() % $rows) + 1;	
			$query = "SELECT * FROM `article_db` WHERE `ID` LIKE '$num';";
			$result = mysqli_query($conn, $query);
			if (mysqli_num_rows($result) > 0){
				$article = mysqli_fetch_assoc($result);
				$response['error'] = false;
				$response['message'] = "Successful";
				$response['title'] = $article['Title'];
				$response['article'] = $article['Article'];
			} else {
				$response['error'] = true;
				$response['message'] = "Some error occur, Try again";				
			}
		} else {
			$response['error'] = true;
			$response['message'] = "No record found";
		}		
	} else {
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}
	
	echo json_encode($response);
?>