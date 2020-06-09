<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $Cod_Praga = $_GET['Cod_Praga'];
    $Status = $_GET['Status'];
    

    $sql = "UPDATE PresencaPraga SET Status ='$Status' 
    WHERE PresencaPraga.fk_Talhao_Cod_Talhao = '$Cod_Talhao'
    AND PresencaPraga.fk_Praga_Cod_Praga = '$Cod_Praga' ";
    
    $dados = $PDO->query($sql);
   
?>