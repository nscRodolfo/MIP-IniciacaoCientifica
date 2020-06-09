<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];
    $Cod_Praga = $_GET['Cod_Praga'];


    $sql = "SELECT PlanoAmostragem.Data
    from PlanoAmostragem
    WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = '$Cod_Talhao'
    AND PlanoAmostragem.fk_Praga_Cod_Praga = '$Cod_Praga' ORDER BY PlanoAmostragem.Data DESC LIMIT 1;";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Data" => $ed->Data);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>