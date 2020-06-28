<?php
    include "conexao.php";

    $link = mysqli_connect("localhost","id11752321_root","123456","id11752321_mip");
    mysqli_set_charset($link, "utf8");


    // pega a variavel da url passada no aplicativo android
    $nome = $_GET['Nome'];
    $telefone = $_GET['Telefone'];
    $email = $_GET['Email'];
    $Cod_Usu = $_GET['Cod_Usu'];

    // verifica se já existe algum cadastro
    $sql = "SELECT email FROM Usuario WHERE email = '$email' and Usuario.Cod_Usuario != '$Cod_Usu'";
    $dados = mysqli_query($link,$sql); //recebe resultado da query do sql

    if(mysqli_num_rows($dados) == 1)
    {
        $verifica = array("confirmacao" => false);
    }else{
        $sql2 = "UPDATE Usuario SET Nome='$nome', Email= '$email', Telefone = '$telefone'
                    WHERE Usuario.Cod_Usuario = '$Cod_Usu'";
        $ex = mysqli_query($link,$sql2); //recebe resultado da query do sql
        $verifica = array("confirmacao" => true);
    }

    echo json_encode($verifica);


?>