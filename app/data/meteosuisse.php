<?php
//cleanup folder
foreach(glob('*.{png,zip}', GLOB_BRACE ) as $file){
	unlink($file);
}
//download and extract
$base = 'http://app-prod-ws.meteoswiss.ch/';
$basePic = 'http://app-prod-static.meteoswiss.ch/';
$json = json_decode(file_get_contents($base . 'precipitationFileList'));
foreach($json as $files){
	foreach($files->files as $file){
		copy($basePic . $file, $file);
		$zip = new ZipArchive;
		if ($zip->open($file) === TRUE) {
			$zip->extractTo('.');
			$zip->close();
		}
	}
}
//create json data file
file_put_contents('data.js', 'var pics=' . json_encode(glob('*.png')) .';');
?>