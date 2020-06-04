<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codP = $_GET["Cod_Planta"];

    // seleciona a propriedade
    $sql = "SELECT FotoPlanta.FotoPlanta 
                   from FotoPlanta
                    WHERE FotoPlanta.fk_Planta_Cod_Planta = '$codP'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("FotoPlanta" => $ed->FotoPlanta);
    }
    echo json_encode($resultado);
   
?>