<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $nome = $_GET['Nome'];
    $cidade = $_GET['Cidade'];
    $estado = $_GET['Estado'];
    $codP= $_GET['Cod_Propriedade'];

    
        $sql2 = "UPDATE Propriedade SET Nome=:NOME, Cidade=:CIDADE, Estado=:ESTADO
                    WHERE Cod_Propriedade = :CODP";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':NOME',$nome);
        $stmt->bindParam(':CIDADE',$cidade);
        $stmt->bindParam(':ESTADO',$estado);
        $stmt->bindParam(':CODP',$codP);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>