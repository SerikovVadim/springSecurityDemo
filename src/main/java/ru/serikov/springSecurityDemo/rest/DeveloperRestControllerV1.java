package ru.serikov.springSecurityDemo.rest;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.serikov.springSecurityDemo.model.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2L, "Petr", "Petrov"),
            new Developer(3L, "Sergey", "Sergeev")
    ).collect(Collectors.toList());


    /**
     * get all Developers
     */
    @GetMapping
    public List<Developer> getAll() {
        return DEVELOPERS;
    }

    /**
     * get developer by id
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Developer getById(@PathVariable long id) {
        return DEVELOPERS.stream()
                .filter(developer -> developer.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public Developer createDeveloper(@RequestBody Developer developer) {
        DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public void deleteById(@PathVariable Long id) {
        DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
    }

}
