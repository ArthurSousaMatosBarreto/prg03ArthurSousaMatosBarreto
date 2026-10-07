Atividades de POO durante a faculdade
Atividade 01- Documento de Requisitos: Incompleto
    Criacao do documento dos casos de usos do projeto final
Atividade 02- Learn git branching: Completo
    aprendizado de git inicial no site learngitbranching
Atividade 03- LearnGIT e Pull Request
    aprendendo a criar uma nova branch e dando pull request

Atividade 13 - Polimorfismo:
## Sobrecarga de construtores (Usuario)
`Usuario()` é usado quando ainda não temos os dados completos do usuário (ex.: formulário em construção).
`Usuario(nome, cpf, login, senha)` é usado no cadastro final, quando já temos os campos obrigatórios para criar a conta.
Atividade 15 - Collections
List vs Map na busca por login
Com a List, buscar um login percorre a lista inteira item por item (pior caso: compara com todos) — com 10 usuários isso é rápido, mas com 10 mil, o tempo cresce proporcionalmente (O(n)).
Com o Map, a busca vai direto na posição calculada pela chave (login), sem percorrer nada — seja com 10 ou 10 mil usuários, o tempo praticamente não muda (O(1)).