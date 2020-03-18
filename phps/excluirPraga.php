<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
 
    $codP= $_GET['Cod_Praga'];
    $codC= $_GET['Cod_Cultura'];

    
        $sql2 = "DELETE FROM PresencaPraga WHERE fk_Praga_Cod_Praga=:CODP AND fk_Cultura_Cod_Cultura=:CODC";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODP',$codP);
        $stmt->bindParam(':CODC',$codC);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>