<?php
$usu = "root";
$pass = "";
$serv = "127.0.0.1";
$db = "contatoteste";

$con = mysqli_connect($serv,$usu,$pass,$db);

if($con){

    $Name = $_GET['name'];
    $query = "insert into Usuario(Nome) values ('".$Name."');";
    $result = mysqli_query($con,$query);
    //x$response = array();

    if($result){
        $status = 'OK';
    }else{
        $status = 'failed';
    }
}else{
    $status = 'failed';
}

echo json_encode(array("response"->$status));

mysqli_close($con);

?>