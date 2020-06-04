<?php
    include "conexao.php";

    $link = mysqli_connect("localhost","id11752321_root","123456","id11752321_mip");
    mysqli_set_charset($link, "utf8");


    // pega a variavel da url passada no aplicativo android
    $nome = $_GET['Nome'];
    $telefone = $_GET['Telefone'];
    $email = $_GET['Email'];
    $senha = $_GET['Senha'];
    $tipoUsu = $_GET['tipoUsu'];

    // verifica se já existe algum cadastro
    $sql = "SELECT email FROM Usuario WHERE email = '$email'";
    $dados = mysqli_query($link,$sql); //recebe resultado da query do sql

    if(mysqli_num_rows($dados) == 1)
    {
        $verifica = array("confirmacao" => false);
    }else{
        $sql2 = "INSERT INTO Usuario(Nome, Telefone, Email, Senha) VALUES (:NOME, :TELEFONE, :EMAIL, :SENHA)";
        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':NOME',$nome);
        $stmt->bindParam(':TELEFONE',$telefone);
        $stmt->bindParam(':EMAIL',$email);
        $stmt->bindParam(':SENHA',$senha);

        // só executa a query depois de receber os valores
        $stmt->execute();

        // após o insert ele pega o codigo para adicionar em produtor ou funcionario
        $sql3 = "SELECT Cod_Usuario FROM Usuario WHERE email = '$email'";

        $aux = mysqli_query($link, $sql3);
        $aux2 = mysqli_fetch_row($aux);
        $codigo = $aux2[0];

        if($tipoUsu == "Funcionario"){
            $sqlFuncionario = "INSERT INTO Funcionario(fk_Usuario_Cod_Usuario) VALUES ($codigo)";
            $runSqlF = mysqli_query($link, $sqlFuncionario);
            $verifica = array("confirmacao" => true);
        }else if ($tipoUsu == "Produtor"){
            $sqlProdutor =  "INSERT INTO Produtor(fk_Usuario_Cod_Usuario) VALUES ($codigo)";
            $runSqlP = mysqli_query($link, $sqlProdutor);
            $verifica = array("confirmacao" => true);
        }
        
        //echo $id[0];

    }

    echo json_encode($verifica);


?>