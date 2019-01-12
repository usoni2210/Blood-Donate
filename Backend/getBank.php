<?php
	
	$response = array();
	$bank = array();
	$key = "Google API Key"; //To Do
	
	if($_SERVER['REQUEST_METHOD'] == "POST"){
		if(isset($_POST["latlag"]) && !empty($_POST["latlag"])){			
			sscanf($_POST["latlag"],"lat/lng: (%f,%f)", $lat, $lng);
			
			$placeSearchURL ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$lat,$lng&radius=15000&key=$key&keyword=Blood%20Bank";
			$placeSearchJSON = file_get_contents($placeSearchURL);
			
			$dataArray = json_decode($placeSearchJSON);

			foreach( $dataArray->results as $details) {               
				$data['name']=$details->name;
				$id = $details->place_id;
				
				$placeid ="https://maps.googleapis.com/maps/api/place/details/json?placeid=$id&key=$key";
				$placeJSONid = file_get_contents($placeid);
				$dataPhone = json_decode($placeJSONid);             
				$data['contact']=$dataPhone->result->formatted_phone_number;
				$data['address']=$dataPhone->result->formatted_address;
				
				array_push($bank, $data);
				if(count($bank)==10)
					break;
			}
			if(count($bank)){
				$response["error"] = false;
				$response["message"] = "Blood Banks Found";
				$response["bank"] = $bank;	
			} else {
				$response["error"] = true;
				$response["message"] = "No Blood Banks Found";
			}
		} else {
			$response["error"] = true;
			$response["message"] = "Required Fields are Missing";
		}
	} else {
		$response["error"] = true;
		$response["message"] = "Invalid Request";
	}
	
	echo json_encode($response);  
?>