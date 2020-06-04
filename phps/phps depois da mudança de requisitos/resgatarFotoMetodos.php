<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codM = $_GET["Cod_Metodo"];

    // seleciona a propriedade
    $sql = "SELECT FotoMetodo.FotoMetodo
                   from FotoMetodo
                    WHERE FotoMetodo.fk_MetodoDeControle_Cod_MetodoControle = '$codM'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("FotoMetodo" => $ed->FotoMetodo);
    }
    echo json_encode($resultado);
   
?>