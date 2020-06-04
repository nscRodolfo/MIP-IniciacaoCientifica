<?php
    include "conexao.php";
    
    $link = mysqli_connect("localhost","id11752321_root","123456","id11752321_mip");
    mysqli_set_charset($link, "utf8");

    // pega a variavel da url passada no aplicativo android
 
    $codP= $_GET['Cod_Propriedade'];
    $codU= $_GET['Cod_Usuario'];

        $sql = "SELECT Cod_Funcionario FROM Funcionario, Usuario
                WHERE Usuario.Cod_Usuario = '$codU'
                AND Funcionario.fk_Usuario_Cod_Usuario = Usuario.Cod_Usuario";

    $aux = mysqli_query($link, $sql);
    $aux2 = mysqli_fetch_row($aux);
    $codF = $aux2[0];

        $sql2 = "DELETE FROM Trabalha WHERE fk_Propriedade_Cod_Propriedade=:CODP
                    AND fk_Funcionario_Cod_Funcionario = :CODF ";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODP',$codP);
        $stmt->bindParam(':CODF',$codF);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>