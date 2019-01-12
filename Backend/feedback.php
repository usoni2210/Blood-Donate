<?php
	$response=array();
  
	if($_SERVER['REQUEST_METHOD']=='POST'){
		if(isset($_POST['email']) && isset($_POST['topic']) && isset($_POST['msg'])){
			$to = ""; //ToDO Email Address to receive feedback

			$useremail=$_POST['email'];
			$topic=$_POST['topic'];        
			$msg=$_POST['msg'];

			$txt = "From : ".$useremail."\n"."\n--Feedback Message--\n\n ".$msg;
			$status = mail($to,$topic,$txt);

			if($status){
				$response['error'] = "false";
				$response['message'] = "Send Successfully";
			} else {
				$response['error'] = "true";
				$response['message'] = "Error in sending";
			}         
		} else {
			$response['error'] = "true";
			$response['message'] = "Required Fields are Missing";
		}
	} else {
		$response['error'] = "true";
		$response['message'] = "Invalid Request";
	}
	
	echo json_encode($response);
?>