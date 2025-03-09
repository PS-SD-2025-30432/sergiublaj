package ro.ps.chefmgmtbackend.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ps.chefmgmtbackend.dto.ChefRequestDTO;
import ro.ps.chefmgmtbackend.dto.ChefResponseDTO;
import ro.ps.chefmgmtbackend.service.ChefService;

@RestController
@RequestMapping("/v1/chef")
@RequiredArgsConstructor
public class ChefController {

    private final ChefService chefService;

    @GetMapping
    public ResponseEntity<List<ChefResponseDTO>> findAll(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        return new ResponseEntity<>(
                chefService.findAll(name),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefResponseDTO> findById(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(
                chefService.findById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ChefResponseDTO> save(@RequestBody ChefRequestDTO chefRequestDTO) {
        return new ResponseEntity<>(
                chefService.save(chefRequestDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChefResponseDTO> update(
            @PathVariable(name = "id") UUID id,
            @RequestBody ChefRequestDTO chefRequestDTO
    ) {
        return new ResponseEntity<>(
                chefService.update(id, chefRequestDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        chefService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
