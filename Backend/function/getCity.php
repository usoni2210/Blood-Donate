<?php
	$key = "Google API Key"; //To Do

function getCity($latitude,$longitude){
    if(!empty($latitude) && !empty($longitude)){
       
	   //Send request and receive json data by address
        $geocodeFromLatLong = file_get_contents('https://maps.googleapis.com/maps/api/geocode/json?latlng='.trim($latitude).','.trim($longitude).'&sensor=true&key='.$key);

        $output = json_decode($geocodeFromLatLong, true);
        $status = $output['status'];
        
		//Get address from json data
        $addr = ($status=="OK")?$output['results'][0]['address_components']:'';
		foreach($addr as $com){
			foreach($com as $type => $arr){
				if($type == "types" && ($arr[0] == "administrative_area_level_2"))
					return $com['long_name'];
			}
		}
    }else{
        return false;   
    }
}

?>