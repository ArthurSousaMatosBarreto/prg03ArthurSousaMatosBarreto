## Atributos de Usuario que não devem aparecer numa tela de listagem
`senha` nunca deve ser exibida (dado sensível). `cpf`, `telefone`, `dataNascimento` e `email` também não precisam estar na listagem, pois são dados pessoais que só fazem sentido no cadastro ou numa tela de detalhe. 
`genero` também fica de fora, porque não ajuda a identificar o usuário na lista. A listagem mostra apenas `nome`, `login` e `tipoUsuario`.
