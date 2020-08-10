<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];

    $sql = "SELECT Aplicacao.Cod_Aplicacao, Aplicacao.Autor, Aplicacao.Data as DataAplicacao, Aplicacao.fk_MetodoDeControle_Cod_MetodoControle as Metodo, Aplicacao.fk_Talhao_Cod_Talhao, Aplicacao.fk_Praga_Cod_Praga
    FROM Aplicacao
    WHERE Aplicacao.fk_Talhao_Cod_Talhao = '$Cod_Talhao'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Aplicacao"=>$ed->Cod_Aplicacao,"Autor"=>$ed->Autor, "DataAplicacao" => $ed->DataAplicacao, "Metodo"=> $ed->Metodo, "fk_Talhao_Cod_Talhao"=> $ed->fk_Talhao_Cod_Talhao, "fk_Praga_Cod_Praga"=> $ed->fk_Praga_Cod_Praga);
    }
    echo json_encode($resultado);
   
?>