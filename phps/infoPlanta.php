<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $codP = $_GET["Cod_Planta"];

    $sql = "SELECT * from Planta WHERE Cod_Planta= '$codP'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "NomeCientifico" => $ed->NomeCientifico, "Familia" => $ed->Familia,
         "Botanica" => $ed->Botanica, "AmbientePropicio" => $ed->AmbientePropicio, "Cultivo" => $ed->Cultivo,
        "TratosCulturais" => $ed->TratosCulturais, "Ciclo" => $ed->Ciclo,
         "TamanhoTalhao" => $ed->TamanhoTalhao);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>