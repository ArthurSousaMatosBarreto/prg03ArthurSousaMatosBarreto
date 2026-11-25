package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;
import java.util.List;

public class UsuarioController implements UsuarioIController {

    private final UsuarioIService service;

    public UsuarioController(UsuarioIService service) {
        this.service = service; // recebe, não cria
    }

    @Override
    public Usuario save(Usuario usuario) {
        return service.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return service.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        service.delete(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return service.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return service.findAll();
    }

    @Override
    public List<Usuario> findByNome(String termo) {
        return service.findByNome(termo);
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        return service.autenticar(login, senha);
    }
}