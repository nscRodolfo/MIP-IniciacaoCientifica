<?php

    $dsn = "mysql:host=localhost;dbname=id11752321_mip;charset=utf8";

    $usuario = "id11752321_root";

    $senha = "123456";


    try{
        $PDO = new PDO($dsn, $usuario, $senha);
    }catch(PDOException $erro){
        echo $erro;
    }

?>