<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $sql = "SELECT Planta.Nome, Planta.Cod_Planta from Planta ORDER BY Planta.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("NomePlanta" => $ed->Nome, "Cod_Planta" => $ed->Cod_Planta);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>