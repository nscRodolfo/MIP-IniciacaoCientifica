<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];

    $sql = "SELECT Aplicacao.Data FROM Aplicacao WHERE Aplicacao.fk_Cultura_Cod_Cultura = '$Cod_Cultura'
                AND Aplicacao.Data = (SELECT MAX(Aplicacao.Data) FROM Aplicacao)";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Data" => $ed->Data);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>