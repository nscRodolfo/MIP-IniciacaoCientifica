<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $email = $_GET['Email'];

    echo sendEmailRecoveryPass($email);

function sendEmailRecoveryPass($email)
{
    $newPass = generatePassword();
    //titulo da mensagem
    $title = "Recuperação de Senha MIP²";

    //mensagem que vai ser enviada, gerando o password random que será guardado no banco de dados
    $message = "Sua nova senha é: " . $newPass . ", faça o login utilizando ela e altere!
                \n Não responda este e-mail";

    //aqui que tá a mágica, funcao mail (propria do php) que envia email do servidor, tem que ver se o 000webhost permite usar ela no modo free
    if (mail($email, $title, $message)) {

        //aqui vc salva no banco de dados a nova senha random no BD, não sei como faz com o volley, ou se quiser pode retornar ela no JSON também
        //só tá retornando em json falando que deu certo, verifica isso no android pra aparecer uma mensagem falando que email foi enviado
        $status = array('sucess' => true);
        echo json_encode($status);

    } else {

        //aqui é quando deu merda, ou veio email vazio ou o servidor não suporta essa função, talvez tenha que pagar não sei mas reza pra dar kkkk
        $status = array('sucess' => false);
        echo json_encode($status);
    }
}

//funcao que peguei na net só pra gerar uma senha segura
function generatePassword($qtyCaraceters = 8)
{
    //Letras minúsculas embaralhadas
    $smallLetters = str_shuffle('abcdefghijklmnopqrstuvwxyz');

    //Letras maiúsculas embaralhadas
    $capitalLetters = str_shuffle('ABCDEFGHIJKLMNOPQRSTUVWXYZ');

    //Números aleatórios
    $numbers = (((date('Ymd') / 12) * 24) + mt_rand(800, 9999));
    $numbers .= 1234567890;
    //Junta tudo
    $characters = $capitalLetters . $smallLetters . $numbers;
    //Embaralha e pega apenas a quantidade de caracteres informada no parâmetro
    $password = substr(str_shuffle($characters), 0, $qtyCaraceters);
    //Retorna a senha
    return $password;
}
   
?>