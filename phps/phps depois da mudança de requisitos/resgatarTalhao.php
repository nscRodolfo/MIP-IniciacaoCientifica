<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];

    // seleciona a propriedade
    $sql = "SELECT Talhao.Cod_Talhao from Talhao WHERE Talhao.fk_Cultura_Cod_Cultura = '$Cod_Cultura'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = 
        array("Cod_Talhao" => $ed->Cod_Talhao);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>