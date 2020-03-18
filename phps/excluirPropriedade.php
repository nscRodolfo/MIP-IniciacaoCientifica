<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
 
    $codP= $_GET['Cod_Propriedade'];

    
        $sql2 = "DELETE FROM Propriedade WHERE Cod_Propriedade=:CODP";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODP',$codP);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>