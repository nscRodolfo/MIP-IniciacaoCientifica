<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codPropriedade = $_GET['Cod_Propriedade'];

    // seleciona a propriedade
    $sql = "SELECT COUNT(*) as count_func
    FROM Usuario, Funcionario, Trabalha
    where Trabalha.fk_Propriedade_Cod_Propriedade = '$codPropriedade'
    and Trabalha.fk_Funcionario_Cod_Funcionario = Funcionario.Cod_Funcionario
    and Funcionario.fk_Usuario_Cod_Usuario = Usuario.Cod_Usuario
";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("count_func" => $ed->count_func);
        $aux = $resultado[0];
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($aux);
   
?>