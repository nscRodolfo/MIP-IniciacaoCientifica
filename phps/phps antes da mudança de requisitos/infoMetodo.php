<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $codM = $_GET["Cod_Metodo"];

    $sql = "SELECT * from MetodoDeControle WHERE Cod_MetodoControle= '$codM'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "MateriaisNecessarios" => $ed->MateriaisNecessarios,
         "ModoDePreparo" => $ed->ModoDePreparo, "IntervaloAplicacao" => $ed->IntervaloAplicacao,
        "EfeitoColateral" => $ed->EfeitoColateral, "Observacoes" => $ed->Observacoes,
         "Atuacao" =>$ed->Atuacao);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>