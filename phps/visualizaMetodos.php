<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $sql = "SELECT MetodoDeControle.Nome, MetodoDeControle.Cod_MetodoControle from MetodoDeControle ORDER BY MetodoDeControle.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("NomeMetodo" => $ed->Nome, "Cod_Metodo" => $ed->Cod_MetodoControle);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>