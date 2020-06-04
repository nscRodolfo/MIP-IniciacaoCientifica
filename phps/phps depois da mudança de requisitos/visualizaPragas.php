<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $sql = "SELECT Praga.Nome, Praga.Cod_Praga from Praga ORDER BY Praga.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("NomePraga" => $ed->Nome, "Cod_Praga" => $ed->Cod_Praga);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>