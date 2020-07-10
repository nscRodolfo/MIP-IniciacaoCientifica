<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codUsuario = $_GET['Cod_Usuario'];

    // seleciona a propriedade
    $sql = "SELECT Propriedade.Cod_Propriedade,
                    Propriedade.Nome,
                     Propriedade.Cidade,
                      Propriedade.Estado,
                       Propriedade.fk_Produtor_Cod_Produtor 
                        FROM Usuario, Funcionario, Trabalha, Propriedade
                         WHERE Usuario.Cod_Usuario = Funcionario.fk_Usuario_Cod_Usuario
                          AND Funcionario.Cod_Funcionario = Trabalha.fk_Funcionario_Cod_Funcionario
                           AND Trabalha.fk_Propriedade_Cod_Propriedade = Propriedade.Cod_Propriedade
                            AND Usuario.Cod_Usuario = '$codUsuario'";
 
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Propriedade" => $ed->Cod_Propriedade, "Nome" => $ed->Nome, "Cidade" => $ed->Cidade, "Estado" => $ed->Estado, "fk_Produtor_Cod_Produtor" => $ed->fk_Produtor_Cod_Produtor);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>