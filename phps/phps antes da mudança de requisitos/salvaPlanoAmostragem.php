<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $Data = $_GET['Data'];
    $PlantasInfestadas = $_GET['PlantasInfestadas'];
    $PlantasAmostradas = $_GET['PlantasAmostradas'];
    $Cod_Praga = $_GET['Cod_Praga'];
    $Autor = $_GET['Autor'];
    
        $sql2 = "INSERT INTO PlanoAmostragem(Autor,Data, PlantasInfestadas,PlantasAmostradas,fk_Talhao_Cod_Talhao,fk_Praga_Cod_Praga) 
                 VALUES (:6,:2,:3,:4,:1,:5)";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':1',$Cod_Talhao);
        $stmt->bindParam(':2',$Data);
        $stmt->bindParam(':3',$PlantasInfestadas);
        $stmt->bindParam(':4',$PlantasAmostradas);
        $stmt->bindParam(':5',$Cod_Praga);
        $stmt->bindParam(':6',$Autor);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>