<?php
    include "conexao.php";

    $link = mysqli_connect("localhost","id11752321_root","123456","id11752321_mip");
    mysqli_set_charset($link, "utf8");


    // pega a variavel da url passada no aplicativo android
    $email = $_GET['Email'];
    $senha = $_GET['Senha'];
    $Cod_Propriedade = $_GET['Cod_Propriedade'];

    // verifica se já existe algum cadastro
    $sql = "SELECT * FROM Usuario 
    WHERE Usuario.email = '$email'
    AND Usuario.senha = '$senha'";
    $dados = mysqli_query($link,$sql); //recebe resultado da query do sql

    if(mysqli_num_rows($dados) == 1)
    {
        $sql2 = "SELECT Funcionario.Cod_Funcionario FROM Usuario, Funcionario
                    WHERE Usuario.email = '$email'
                    AND Usuario.senha = '$senha'
                    AND Funcionario.fk_Usuario_Cod_Usuario = Usuario.Cod_Usuario";
        
        $aux = mysqli_query($link, $sql2);
        $aux2 = mysqli_fetch_row($aux);
        $codigoUsuario = $aux2[0];
        

        $sql3 = "INSERT INTO Trabalha(fk_Propriedade_Cod_Propriedade, fk_Funcionario_Cod_Funcionario) 
                 VALUES (:CODP,:CODF)";
        // prepara o statment
        $stmt = $PDO->prepare($sql3);
        //statment
        $stmt->bindParam(':CODP',$Cod_Propriedade);
        $stmt->bindParam(':CODF',$codigoUsuario);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);
        
    }else{
        $verifica = array("confirmacao" => false);
    }

    echo json_encode($verifica);


?>