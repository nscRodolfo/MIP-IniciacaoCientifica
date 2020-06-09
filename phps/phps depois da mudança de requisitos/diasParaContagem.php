<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $dataAplicacao = null;


    $sql = "SELECT Aplicacao.Data, MetodoDeControle.IntervaloAplicacao
    FROM Aplicacao, MetodoDeControle
    WHERE Aplicacao.Data = (SELECT MAX(Aplicacao.Data) FROM Aplicacao WHERE Aplicacao.fk_Talhao_Cod_Talhao = '$Cod_Talhao')
    AND Aplicacao.fk_Talhao_Cod_Talhao = '$Cod_Talhao'
    AND Aplicacao.fk_MetodoDeControle_Cod_MetodoControle = MetodoDeControle.Cod_MetodoControle";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $dataAplicacao = $ed->Data;
        $intervaloParaAdicao = $ed->IntervaloAplicacao;
    }

    if($dataAplicacao==null){
        $resultado [] = array("DiasPraContagem" => "0");
        echo json_encode($resultado);
    }else{
        $dataAtual = date('Y-m-d');

        $auxDias = " + $intervaloParaAdicao days";
        $dataFuturaContagem = date('Y-m-d', strtotime($dataAplicacao. $auxDias));

        if($dataFuturaContagem > $dataAtual){
            $data_inicio = new DateTime("$dataAtual");
            $data_fim = new DateTime("$dataFuturaContagem");

            $DiasPraContagem =  $data_inicio->diff($data_fim);

            $resultado [] = array("DiasPraContagem" => $DiasPraContagem->days);
        }else{
            $sql2 = "UPDATE Talhao SET Aplicado = 0
                WHERE Talhao.Cod_Talhao = '$Cod_Talhao'";
                
            $tiraAplica = $PDO->query($sql2);

            $resultado [] = array("DiasPraContagem" => "0");
        }
        echo json_encode($resultado);
    }

?>