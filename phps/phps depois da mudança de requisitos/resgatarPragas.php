<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];

    // seleciona a propriedade
    $sql = "SELECT Praga.Nome, Praga.Cod_Praga, PresencaPraga.Status
                from Praga, PresencaPraga
                WHERE PresencaPraga.fk_Talhao_Cod_Talhao = '$Cod_Talhao' 
                and PresencaPraga.fk_Praga_Cod_Praga = Praga.Cod_Praga
                ORDER BY PresencaPraga.Status DESC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "Cod_Praga" => $ed->Cod_Praga, "Status" => $ed->Status);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>