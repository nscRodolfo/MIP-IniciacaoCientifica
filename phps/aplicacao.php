<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];
    $Cod_Praga = $_GET['Cod_Praga'];
    $Data = $_GET['Data'];
    $Cod_Metodo = $_GET['Cod_Metodo'];
    
    // seleciona a propriedade
    $doideira = "INSERT into Aplicacao (Data, fk_MetodoDeControle_Cod_MetodoControle, fk_Cultura_Cod_Cultura) 
                VALUES ('$Data','$Cod_Metodo','$Cod_Cultura')";

    $sql = "UPDATE PresencaPraga SET Status = 1
            WHERE PresencaPraga.fk_Cultura_Cod_Cultura = '$Cod_Cultura'
            AND PresencaPraga.fk_Praga_Cod_Praga = '$Cod_Praga'";

    $sql3 = "UPDATE Cultura SET Aplicado = 1
                WHERE Cultura.Cod_Cultura = '$Cod_Cultura'";
    
    $dados = $PDO->query($sql);
    $dados2 = $PDO->query($doideira);
    $dados3 = $PDO->query($sql3);
    
?>