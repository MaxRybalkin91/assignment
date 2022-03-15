package ru.test.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.test.assignment.model.ProxyType;
import ru.test.assignment.model.rest.ProxyRq;
import ru.test.assignment.model.rest.ProxyRs;
import ru.test.assignment.service.ProxyService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Validated
public class AssignmentController {
    private final ProxyService proxyService;

    @PostMapping(path = "/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@Valid @RequestBody ProxyRq request) {
        proxyService.save(request);
        return ResponseEntity.ok("");
    }

    @PutMapping(path = "/edit", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> edit(@Valid @RequestBody ProxyRq request) {
        proxyService.update(request);
        return ResponseEntity.ok("");
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public Page<ProxyRs> findAll() {
        return proxyService.findAll();
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ProxyRs getById(@PathVariable Long id) {
        return proxyService.getById(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<ProxyRs> findAllByNameAndType(@RequestParam String name,
                                              @RequestParam ProxyType type) {
        return proxyService.findAllByNameAndType(name, type);
    }

    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        proxyService.deleteById(id);
        return ResponseEntity.ok("");
    }
}
