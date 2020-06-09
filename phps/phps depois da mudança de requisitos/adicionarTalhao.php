<?php
    include "conexao.php";

    $link = mysqli_connect("localhost","id11752321_root","123456","id11752321_mip");
    mysqli_set_charset($link, "utf8");


    // pega a variavel da url passada no aplicativo android
    $nomeTalhao = $_GET['NomeTalhao'];
    $codCultura = $_GET['Cod_Cultura'];

    $sql = "SELECT Planta.Cod_Planta FROM Planta, Cultura 
    WHERE Planta.Cod_Planta = Cultura.fk_Planta_Cod_Planta
    AND Cultura.Cod_Cultura = '$codCultura'";
    $dados = mysqli_query($link,$sql);
    $aux2 = mysqli_fetch_row($dados);
    $codPlanta = $aux2[0];

        $sql2 = "INSERT into Talhao(Nome, fk_Cultura_Cod_Cultura, fk_Planta_Cod_Planta) values (:NOME, :CODC, :CODP)
        ;";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':NOME',$nomeTalhao);
        $stmt->bindParam(':CODC',$codCultura);
        $stmt->bindParam(':CODP',$codPlanta);
        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>