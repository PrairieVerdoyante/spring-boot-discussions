package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.UserDTO;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO dto) {
        userService.createUser(dto.getUsername(), dto.getPassword(), dto.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
