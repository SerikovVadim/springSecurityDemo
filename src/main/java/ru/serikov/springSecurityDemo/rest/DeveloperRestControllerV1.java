package ru.serikov.springSecurityDemo.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.serikov.springSecurityDemo.model.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
        private List<Developer> DEVELOPERS = Stream.of(
                new Developer(1L,"Ivan", "Ivanov"),
                new Developer(2L,"Petr", "Petrov"),
                new Developer(3L, "Sergey","Sergeev")
        ).collect(Collectors.toList());


    @GetMapping
    public List<Developer> getAll(){
        return DEVELOPERS;
    }


    @GetMapping("{id}")
    public Developer getById(@PathVariable long id){
        return DEVELOPERS.stream().filter( developer -> developer.getId()==id).findFirst().orElse(null);
    }

}
