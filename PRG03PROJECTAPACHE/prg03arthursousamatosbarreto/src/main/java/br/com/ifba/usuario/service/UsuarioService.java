package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.exception.NegocioException;
import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import br.com.ifba.usuario.validar.ValidadorCadastro;
import java.util.List;

public class UsuarioService implements UsuarioIService {

    private final UsuarioIDao dao;

    // quem monta o objeto decide qual DAO entra: o Service só declara que precisa de um
    public UsuarioService(UsuarioIDao dao) {
        this.dao = dao;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (!ValidadorCadastro.cpfValido(usuario.getCpf())) {
            throw new NegocioException("CPF inválido.");
        }
        if (!ValidadorCadastro.senhaForte(usuario.getSenha())) {
            throw new NegocioException("Senha deve ter entre "
                    + ValidadorCadastro.TAMANHO_SENHA_MINIMO + " e "
                    + ValidadorCadastro.TAMANHO_SENHA_MAXIMO + " caracteres.");
        }
        validarLogin(usuario, null);
        return dao.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        dao.findById(usuario.getId()); // lança UsuarioNaoEncontradoException se não existir
        validarLogin(usuario, usuario.getId());
        return dao.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        dao.delete(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Usuario> findByNome(String termo) {
        return dao.findByNome(termo);
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        Usuario usuario;
        try {
            usuario = dao.findByLogin(login);
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException("Login ou senha incorretos.");
        }
        if (!usuario.autenticar(login, senha)) {
            throw new NegocioException("Login ou senha incorretos.");
        }
        if (!usuario.isAtivo()) {
            throw new NegocioException("Usuário inativo não pode entrar.");
        }
        return usuario;
    }

    // login: sem palavra proibida e sem repetir (idIgnorado = o próprio usuário, na edição)
    private void validarLogin(Usuario usuario, Long idIgnorado) {
        if (ValidadorCadastro.contemPalavraProibida(usuario.getLogin())) {
            throw new NegocioException("Login contém palavra não permitida.");
        }
        if (dao.existsByLogin(usuario.getLogin(), idIgnorado)) {
            throw new NegocioException("Já existe um usuário com o login: " + usuario.getLogin());
        }
    }
}