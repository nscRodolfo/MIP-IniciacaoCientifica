<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codP = $_GET["Cod_Praga"];

    // seleciona a propriedade
    $sql = "SELECT FotoPraga.FotoPraga 
                   from FotoPraga
                    WHERE FotoPraga.fk_Praga_Cod_Praga = '$codP'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("FotoPraga" => $ed->FotoPraga);
    }
    echo json_encode($resultado);
   
?>