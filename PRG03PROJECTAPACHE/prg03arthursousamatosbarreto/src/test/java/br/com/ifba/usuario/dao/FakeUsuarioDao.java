package br.com.ifba.usuario.dao;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeUsuarioDao implements UsuarioIDao {

    private final List<Usuario> lista = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setId(proximoId++);
        lista.add(usuario);
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(usuario.getId())) {
                lista.set(i, usuario);
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException(usuario.getId());
    }

    @Override
    public void delete(Usuario usuario) {
        lista.removeIf(u -> u.getId().equals(usuario.getId()));
    }

    @Override
    public Usuario findById(Long id) {
        return lista.stream().filter(u -> u.getId().equals(id)).findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(lista);
    }

    @Override
    public Usuario findByLogin(String login) {
        return lista.stream().filter(u -> u.getLogin().equals(login)).findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException(login));
    }

    @Override
    public List<Usuario> findByNome(String termo) {
        return lista.stream()
                .filter(u -> u.getNome().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByLogin(String login, Long idIgnorado) {
        return lista.stream().anyMatch(u -> u.getLogin().equals(login)
                && (idIgnorado == null || !idIgnorado.equals(u.getId())));
    }
}