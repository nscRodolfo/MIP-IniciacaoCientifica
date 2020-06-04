<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Praga = $_GET['Cod_Praga'];

    // seleciona a propriedade
    $sql = "SELECT Praga.Localizacao
                from Praga
                WHERE Praga.Cod_Praga = '$Cod_Praga' ";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Localizacao" => $ed->Localizacao);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>