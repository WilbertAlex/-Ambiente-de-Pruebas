package pe.edu.upeu.turismospringboot.service;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.turismospringboot.model.dto.UsuarioCompletoDto;
import pe.edu.upeu.turismospringboot.model.dto.UsuarioDtoUser;
import pe.edu.upeu.turismospringboot.model.dto.UsuarioIdMensajeDtoResponse;
import pe.edu.upeu.turismospringboot.model.entity.Usuario;

import java.util.List;

public interface UsuarioCompletoService {
    public List<Usuario> listarUsuarioCompleto();
    public Usuario buscarUsuarioCompletoPorId(Long idUsuario);
    public Usuario crearUsuarioCompleto(UsuarioCompletoDto usuarioCompleto, MultipartFile file);
    public Usuario actualizarUsuarioCompleto(Long idUsuario, UsuarioCompletoDto usuarioCompleto, MultipartFile file);
    public void eliminarUsuarioCompleto(Long idUsuario);
    List<Usuario> buscarUsuariosPorUsername(String username);
    public Usuario actualizarUsuarioCompletoPorUsuario(Long idUsuario, UsuarioDtoUser UsuarioDtoUser, MultipartFile file, Usuario usuarioAutenticado);
    public UsuarioIdMensajeDtoResponse buscarIdUsuarioPorUsername(String username);
}
