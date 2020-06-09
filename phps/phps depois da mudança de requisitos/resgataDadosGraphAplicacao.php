<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $Cod_Praga = $_GET['Cod_Praga'];

    $sql = "SELECT MetodoDeControle.Nome as Metodo, Aplicacao.Autor, Aplicacao.Data as DataAplicacao, Aplicacao_Plano.DataPlano, Aplicacao_Plano.PlantasInfestadas as popPragas, Aplicacao_Plano.PlantasAmostradas as numPlantas
    FROM Aplicacao_Plano, Aplicacao, MetodoDeControle
    WHERE Aplicacao.fk_Talhao_Cod_Talhao = '$Cod_Talhao'
    AND Aplicacao.fk_Praga_Cod_Praga = '$Cod_Praga'
    AND Aplicacao.Cod_Aplicacao = Aplicacao_Plano.fk_Aplicacao_Cod_Aplicacao
    AND Aplicacao.fk_MetodoDeControle_Cod_MetodoControle = MetodoDeControle.Cod_MetodoControle";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Autor"=>$ed->Autor, "Metodo"=> $ed->Metodo, "DataAplicacao" => $ed->DataAplicacao,"DataPlano" => $ed->DataPlano,"popPragas" => $ed->popPragas,"numPlantas" => $ed->numPlantas);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>