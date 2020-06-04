<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $nome = $_GET['Nome'];
    $cidade = $_GET['Cidade'];
    $estado = $_GET['Estado'];
    $fk_Produtor_Cod_Produtor = $_GET['fk_Produtor_Cod_Produtor'];

    
        $sql2 = "INSERT INTO Propriedade(Nome, Cidade, Estado, fk_Produtor_Cod_Produtor) VALUES (:NOME, :CIDADE, :ESTADO, :COD_PRODUTOR)";
        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':NOME',$nome);
        $stmt->bindParam(':CIDADE',$cidade);
        $stmt->bindParam(':ESTADO',$estado);
        $stmt->bindParam(':COD_PRODUTOR',$fk_Produtor_Cod_Produtor);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>