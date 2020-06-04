<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];
    $Cod_Praga = $_GET['Cod_Praga'];
    $Status = $_GET['Status'];
    

    $sql = "UPDATE PresencaPraga SET Status ='$Status' 
    WHERE PresencaPraga.fk_Cultura_Cod_Cultura = '$Cod_Cultura'
    AND PresencaPraga.fk_Praga_Cod_Praga = '$Cod_Praga' ";
    
    $dados = $PDO->query($sql);
   
?>