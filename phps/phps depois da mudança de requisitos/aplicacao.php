<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $Cod_Praga = $_GET['Cod_Praga'];
    $Data = $_GET['Data'];
    $Cod_Metodo = $_GET['Cod_Metodo'];
    $Autor = $_GET['Autor'];
    // seleciona a propriedade
    $doideira = "INSERT into Aplicacao (Autor, Data, fk_MetodoDeControle_Cod_MetodoControle, fk_Talhao_Cod_Talhao, fk_Praga_Cod_Praga) 
                VALUES ('$Autor','$Data','$Cod_Metodo','$Cod_Talhao','$Cod_Praga')";

    $sql = "UPDATE PresencaPraga SET Status = 1
            WHERE PresencaPraga.fk_Talhao_Cod_Talhao = '$Cod_Talhao'
            AND PresencaPraga.fk_Praga_Cod_Praga = '$Cod_Praga'";

    $sql3 = "UPDATE Talhao SET Aplicado = 1
                WHERE Talhao.Cod_Talhao = '$Cod_Talhao'";
    
    $dados = $PDO->query($sql);
    $dados2 = $PDO->query($doideira);
    $dados3 = $PDO->query($sql3);
    
?>