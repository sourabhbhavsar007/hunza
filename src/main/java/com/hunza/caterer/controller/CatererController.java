package com.hunza.caterer.controller;

import com.hunza.caterer.dto.CatererDTO;
import com.hunza.caterer.dto.CatererDTOList;
import com.hunza.caterer.service.CatererService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/caterer")
@RequiredArgsConstructor
public class CatererController
{
    private final CatererService catererService;

    @PostMapping(value = { "", "/create" },
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatererDTO> create(@Valid @RequestBody CatererDTO catererDTO)
    {
        return new ResponseEntity<>(catererService.create(catererDTO), CREATED);
    }

    @GetMapping(value = { "/{id}", "/get/{id}" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatererDTO> get(@PathVariable("id") String id)
    {
        return new ResponseEntity<>(catererService.get(id), OK);
    }

    @GetMapping(value =  "/getByName/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CatererDTO>> getByName(@PathVariable(name = "name") String name)
    {
        return new ResponseEntity<>(catererService.getByName(name), OK);
    }

    @GetMapping(value =  "/listByCity/{city}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatererDTOList> listByCity(@PathVariable("city") String city,
                                                     @RequestParam(name = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize)
    {
        return new ResponseEntity<>(addLinkList(catererService.listByCity(city, of(pageIndex, pageSize))), OK);
    }

    public static CatererDTOList addLinkList( CatererDTOList catererDTOList )
    {
        List<CatererDTO> outputCaterer = new ArrayList<>( catererDTOList.getCatererDTOS().size() );
        catererDTOList.getCatererDTOS().forEach(ctr ->
                outputCaterer.add( addLink( ctr )));

        catererDTOList.setCatererDTOS(outputCaterer);
        return catererDTOList;
    }

    public static CatererDTO addLink( CatererDTO catererDTO )
    {
        catererDTO.add( (linkTo( methodOn( CatererController.class ).get( catererDTO.getId() ) )).withRel( "get" ) );
        catererDTO.add( (linkTo( methodOn( CatererController.class ).getByName(catererDTO.getName() ) )).withRel( "getByName" ) );
        return catererDTO;
    }
}
