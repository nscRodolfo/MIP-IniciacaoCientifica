<?php
    include "conexao.php";

    $link = mysqli_connect("localhost","id11752321_root","123456","id11752321_mip");
    mysqli_set_charset($link, "utf8");

    // pega a variavel da url passada no aplicativo android
    $Cod_Praga = $_GET['Cod_Praga'];
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $Status = $_GET['Status'];

    // verifica se já existe algum cadastro
    $sql = "SELECT * FROM PresencaPraga 
    WHERE PresencaPraga.fk_Talhao_Cod_Talhao = '$Cod_Talhao' AND PresencaPraga.fk_Praga_Cod_Praga = '$Cod_Praga'";
    $dados = mysqli_query($link,$sql); //recebe resultado da query do sql

    if(mysqli_num_rows($dados) > 0)
    { 
        $sql2 = "UPDATE PresencaPraga SET PresencaPraga.Status =:STATUS WHERE PresencaPraga.fk_Talhao_Cod_Talhao = :CODTALHAO AND PresencaPraga.fk_Praga_Cod_Praga = :CODPRAGA";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':STATUS',$Status);
        $stmt->bindParam(':CODPRAGA',$Cod_Praga);
        $stmt->bindParam(':CODTALHAO',$Cod_Talhao);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    }else{
        $sql3 = "INSERT INTO PresencaPraga(Status, fk_Praga_Cod_Praga, fk_Talhao_Cod_Talhao) VALUES (:STATUS, :CODPRAGA,:CODTALHAO)";

        // prepara o statment
        $stmt = $PDO->prepare($sql3);
        //statment
        $stmt->bindParam(':STATUS',$Status);
        $stmt->bindParam(':CODPRAGA',$Cod_Praga);
        $stmt->bindParam(':CODTALHAO',$Cod_Talhao);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    }
    echo json_encode($verifica);

?>