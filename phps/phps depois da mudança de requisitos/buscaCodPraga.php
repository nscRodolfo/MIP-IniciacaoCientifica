<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];

    $sql = "SELECT Aplicacao.fk_Praga_Cod_Praga
    FROM Aplicacao
    WHERE Aplicacao.Data = (SELECT MAX(Aplicacao.Data) FROM Aplicacao)
    AND Aplicacao.fk_Talhao_Cod_Talhao = '$Cod_Talhao'";
    

    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("fk_Praga_Cod_Praga" => $ed->fk_Praga_Cod_Praga);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>  