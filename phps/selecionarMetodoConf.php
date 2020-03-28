<?php
    include "conexao.php";

    $cod_Praga = $_GET["cod_Praga"];

    $sql = "SELECT MetodoDeControle.Nome, MetodoDeControle.Cod_MetodoControle, MetodoDeControle.IntervaloAplicacao 
    from MetodoDeControle, Praga, Controla
    WHERE Praga.Cod_Praga = '$cod_Praga'
    and Controla.fk_Praga_Cod_Praga = Praga.Cod_Praga 
    and Controla.fk_MetodoDeControle_Cod_MetodoControle = MetodoDeControle.Cod_MetodoControle
    ORDER BY MetodoDeControle.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "Cod_MetodoControle" => $ed->Cod_MetodoControle, "IntervaloAplicacao" => $ed->IntervaloAplicacao);
       
    }
    echo json_encode($resultado);
   
?>