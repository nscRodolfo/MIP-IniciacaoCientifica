<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Praga = $_GET['Cod_Praga'];
    $Cod_Cultura = $_GET['Cod_Cultura'];

    
        $sql2 = "INSERT INTO PresencaPraga(fk_Praga_Cod_Praga, fk_Cultura_Cod_Cultura) VALUES (:CODPRAGA,:CODCULTURA)";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODPRAGA',$Cod_Praga);
        $stmt->bindParam(':CODCULTURA',$Cod_Cultura);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>