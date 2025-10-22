package pe.edu.upeu.turismospringboot.controller.emprendedor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.turismospringboot.model.dto.UsuarioCompletoDto;
import pe.edu.upeu.turismospringboot.model.dto.UsuarioDtoUser;
import pe.edu.upeu.turismospringboot.model.dto.UsuarioIdMensajeDtoResponse;
import pe.edu.upeu.turismospringboot.model.entity.Usuario;
import pe.edu.upeu.turismospringboot.service.UsuarioCompletoService;

@RestController
@RequestMapping("/emprendedor/usuarioCompleto")
public class UsuarioControllerEmprendedor {

    @Autowired
    private UsuarioCompletoService usuarioCompletoService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> getUsuarioById(
            @PathVariable Long idUsuario
    ){
        return ResponseEntity.ok(usuarioCompletoService.buscarUsuarioCompletoPorId(idUsuario));
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> putUsuario(
            @PathVariable Long idUsuario,
            @RequestPart(value = "usuario") String usuarioJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            UsuarioDtoUser usuarioDtoUser = objectMapper.readValue(usuarioJson, UsuarioDtoUser.class);

            Usuario usuarioActualizado = usuarioCompletoService.actualizarUsuarioCompletoPorUsuario(idUsuario, usuarioDtoUser, file, usuarioAutenticado);
            return ResponseEntity.ok(usuarioActualizado);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscarIdPorUsername/{userName}")
    public ResponseEntity<UsuarioIdMensajeDtoResponse> buscarIdPorUsername(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(usuarioCompletoService.buscarIdUsuarioPorUsername(userName));
    }
}
