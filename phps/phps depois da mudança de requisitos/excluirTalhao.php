<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
 
    $codT= $_GET['Cod_Talhao'];

    
        $sql2 = "DELETE FROM Talhao WHERE Cod_Talhao=:CODT";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODT',$codT);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>