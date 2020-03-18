<?php
    include "conexao.php";
    $email = $_GET['email'];

    $sql = "SELECT * FROM Usuario WHERE email = '$email'";
    $dados = $PDO->query($sql); //recebe resultado da query do sql
    $resultado = array();
    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Usuario" => $ed->Cod_Usuario, "Email" => $ed->Email, "Senha" => $ed->Senha, "Nome" => $ed->Nome, "Telefone" => $ed->Telefone);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado); //passa pro json todos os atributos retornados



?>