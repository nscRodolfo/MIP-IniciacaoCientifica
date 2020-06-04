<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codUsuario = $_GET['Cod_Usuario'];

    // seleciona a propriedade
    $sql = "SELECT Cod_Produtor
                   from Produtor
                    WHERE fk_Usuario_Cod_Usuario = '$codUsuario';";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Produtor" => $ed->Cod_Produtor);
        $aux = $resultado[0];
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($aux);
   
?>