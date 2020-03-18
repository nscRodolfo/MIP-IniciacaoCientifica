<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $codP = $_GET["Cod_Praga"];

    $sql = "SELECT * from Praga WHERE Cod_Praga= '$codP'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "Familia" => $ed->Familia, "Ordem" => $ed->Ordem,
         "Descricao" => $ed->Descricao, "NomeCientifico" => $ed->NomeCientifico, "Localizacao" => $ed->Localizacao,
        "AmbientePropicio" => $ed->AmbientePropicio, "CicloVida" => $ed->CicloVida,
         "Injurias" => $ed->Injurias, "Observacoes" => $ed->Observacoes, "HorarioDeAtuacao" => $ed->HorarioDeAtuacao,
          "EstagioDeAtuacao" => $ed->EstagioDeAtuacao, "ControleCultural" =>$ed->ControleCultural);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>