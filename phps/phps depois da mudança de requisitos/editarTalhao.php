<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $nomeTalhao = $_GET['NomeTalhao'];
    $codT= $_GET['Cod_Talhao'];

    
        $sql2 = "UPDATE Talhao SET Nome=:NOME
                    WHERE Cod_Talhao = :CODT";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':NOME',$nomeTalhao);
        $stmt->bindParam(':CODT',$codT);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>