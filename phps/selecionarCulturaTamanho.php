<?php
    include "conexao.php";

    $sql = "SELECT Nome, TamanhoTalhao, Cod_Planta from Planta";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "TamanhoTalhao" => $ed->TamanhoTalhao, "Cod_Planta" => $ed->Cod_Planta);
       
    }
    echo json_encode($resultado);
   
?>