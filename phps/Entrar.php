<?php
    include "conexao.php";
    $email = $_GET['email'];

    $sqlprod = "SELECT * FROM Usuario,Produtor WHERE email = '$email'
                AND Usuario.Cod_Usuario = Produtor.fk_Usuario_Cod_Usuario";
    
    $sqladm = " SELECT * FROM Usuario,Administrador WHERE email = '$email'
                AND Usuario.Cod_Usuario = Administrador.fk_Usuario_Cod_Usuario";

    $sqlfunc ="SELECT * FROM Usuario,Funcionario WHERE email = '$email'
                AND Usuario.Cod_Usuario = Funcionario.fk_Usuario_Cod_Usuario";

    $dadosprod = $PDO->query($sqlprod); //recebe resultado da query do sql

    $dadosadm = $PDO->query($sqladm); //recebe resultado da query do sql

    $dadosfunc = $PDO->query($sqlfunc); //recebe resultado da query do sql

    $resultadofunc = array();
    $resultadoadm = array();
    $resultadoprod = array();
    while ($edfunc = $dadosfunc->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultadofunc [] = array("Tipo" => "Funcionario","Cod_Usuario" => $edfunc->Cod_Usuario, "Email" => $edfunc->Email, "Senha" => $edfunc->Senha, "Nome" => $edfunc->Nome, "Telefone" => $edfunc->Telefone);
        
    }
    while ($edprod = $dadosprod->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultadoprod [] = array("Tipo" => "Produtor", "Cod_Usuario" => $edprod->Cod_Usuario, "Email" => $edprod->Email, "Senha" => $edprod->Senha, "Nome" => $edprod->Nome, "Telefone" => $edprod->Telefone);
        
    }
    while ($edadm = $dadosadm->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultadoadm [] = array("Tipo" => "Adm", "Cod_Usuario" => $edadm->Cod_Usuario, "Email" => $edadm->Email, "Senha" => $edadm->Senha, "Nome" => $edadm->Nome, "Telefone" => $edadm->Telefone);
        
    }

    if(!empty($resultadoadm)){

        echo json_encode($resultadoadm);

    }else if(!empty($resultadofunc)){

        echo json_encode($resultadofunc);

    }else if(!empty($resultadoprod)){

        echo json_encode($resultadoprod);

    }
     //passa pro json todos os atributos retornados



?>