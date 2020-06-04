<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codPropriedade = $_GET['Cod_Propriedade'];

    // seleciona a propriedade
    $sql = "SELECT Usuario.Cod_Usuario, Usuario.Nome, Usuario.Telefone, Usuario.Email 
            FROM Usuario, Funcionario, Trabalha
            where Trabalha.fk_Propriedade_Cod_Propriedade = '$codPropriedade'
            and Trabalha.fk_Funcionario_Cod_Funcionario = Funcionario.Cod_Funcionario
            and Funcionario.fk_Usuario_Cod_Usuario = Usuario.Cod_Usuario";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Usuario" => $ed->Cod_Usuario, "Nome" => $ed->Nome, "Telefone" => $ed->Telefone, "Email" => $ed->Email);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>