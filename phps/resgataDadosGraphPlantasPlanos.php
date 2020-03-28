<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];
    $Cod_Praga = $_GET['Cod_Praga'];

    $sql = "SELECT PlanoAmostragem.Data, SUM(PlanoAmostragem.PlantasInfestadas) as popPragas, SUM(PlanoAmostragem.PlantasAmostradas) as numPlantas
    from PlanoAmostragem, Talhao
    WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = Talhao.Cod_Talhao
    AND PlanoAmostragem.fk_Praga_Cod_Praga = '$Cod_Praga'
    AND Talhao.fk_Cultura_Cod_Cultura = '$Cod_Cultura'
    GROUP BY PlanoAmostragem.Data";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Data" => $ed->Data,"popPragas" => $ed->popPragas,"numPlantas" => $ed->numPlantas);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>