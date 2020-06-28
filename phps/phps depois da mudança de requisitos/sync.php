<?php
$usu = "id11752321_root";
$pass = "123456";
$serv = "localhost";
$db = "id11752321_mip";

$con = mysqli_connect($serv,$usu,$pass,$db);

if($con){
    $Nome = $_GET['Nome'];
    $query = "insert into testeSync(Nome) values ('".$Nome."');";
    $result = mysqli_query($con,$query);
    //x$response = array();

    if($result){
        $status = 'OK';
    }else{
        $status = 'FAILED';
    }
}else{
    $status = 'FAILED';
}

echo json_encode(array("response" => $status));

mysqli_close($con);

?>