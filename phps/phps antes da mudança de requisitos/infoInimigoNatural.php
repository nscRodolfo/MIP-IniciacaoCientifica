<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android

    // seleciona a propriedade
    $codI = $_GET["Cod_Inimigo"];

    $sql = "SELECT * from InimigoNatural WHERE Cod_Inimigo= '$codI'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>