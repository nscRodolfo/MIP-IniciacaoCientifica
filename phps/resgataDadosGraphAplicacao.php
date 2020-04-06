<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];
    $Cod_Praga = $_GET['Cod_Praga'];

    $sql = "SELECT Aplicacao.Data as DataAplicacao, Aplicacao_Plano.DataPlano, Aplicacao_Plano.PlantasInfestadas as popPragas, Aplicacao_Plano.PlantasAmostradas as numPlantas
    FROM Aplicacao_Plano, Aplicacao
    WHERE Aplicacao.fk_Cultura_Cod_Cultura = '$Cod_Cultura'
    AND Aplicacao.fk_Praga_Cod_Praga = '$Cod_Praga'
    AND Aplicacao.Cod_Aplicacao = Aplicacao_Plano.fk_Aplicacao_Cod_Aplicacao";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("DataAplicacao" => $ed->DataAplicacao,"DataPlano" => $ed->DataPlano,"popPragas" => $ed->popPragas,"numPlantas" => $ed->numPlantas);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>