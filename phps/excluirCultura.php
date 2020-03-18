<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
 
    $codC= $_GET['Cod_Cultura'];

    
        $sql2 = "DELETE FROM Cultura WHERE Cod_Cultura=:CODC";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODC',$codC);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>