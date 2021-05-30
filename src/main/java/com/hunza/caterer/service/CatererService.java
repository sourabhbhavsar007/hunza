package com.hunza.caterer.service;

import com.hunza.caterer.domain.Caterer;
import com.hunza.caterer.dto.CatererDTO;
import com.hunza.caterer.dto.CatererDTOList;
import com.hunza.caterer.dto.util.PagingInfo;
import com.hunza.caterer.exception.MandatoryDataMissingException;
import com.hunza.caterer.exception.NotFoundException;
import com.hunza.caterer.repository.CatererRepository;
import com.hunza.caterer.repository.KafkaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatererService
{
    private static final String CATERER_CREATION_MESSAGE = "Added a new Caterer in System";

    @Value("${caterer.kafka.topic}")
    private String kafkaTopic;

    private final CatererRepository catererRepository;
    private final KafkaRepository kafkaRepository;

    public CatererDTO create(CatererDTO catererDTO)
    {
        log.info("incoming payload for /create : {}", catererDTO.toString());

        validateCaterer(catererDTO);

        Caterer caterer = new Caterer();
        catererDTO.fillEntity(caterer);

        catererDTO = new CatererDTO().fillDTO(catererRepository.insert(caterer));
        kafkaRepository.sendAsync(kafkaTopic, CATERER_CREATION_MESSAGE);

        log.info("Returning payload for /create : {}", catererDTO.toString());
        return catererDTO;
    }

    public CatererDTO get(String id)
    {
        CatererDTO catererDTO = new CatererDTO().fillDTO(findById(id));
        log.info("Returning payload for /get : {}", catererDTO.toString());
        return catererDTO;
    }

    public List<CatererDTO> getByName(String name)
    {
        List<Caterer> caterers = catererRepository.findByName(name);
        if (caterers.isEmpty())
            throw new NotFoundException("Caterer not found with name " + name);

        return caterers.stream().<CatererDTO>map(ctr -> new CatererDTO().fillDTO(ctr))
                .collect(Collectors.toList());
    }

    @Cacheable("caterersByCity")
    public CatererDTOList listByCity(String city, PageRequest pageRequest)
    {
        Page<Caterer> catererPage = catererRepository.findByAddress_City(city, pageRequest);
        if (catererPage.isEmpty())
            throw new NotFoundException("Caterer not found with city name " + city);

        CatererDTOList catererDTOList = new CatererDTOList();
        catererDTOList.setPagingInfo(new PagingInfo(pageRequest, catererPage.getTotalElements()));
        catererDTOList.setCatererDTOS(catererPage.getContent().stream().<CatererDTO>map(ctr -> new CatererDTO().fillDTO(ctr))
                .collect(Collectors.toList()));

        log.info("Returning payload for /listByCity : {}", catererDTOList.toString());
        return catererDTOList;
    }

    private void validateCaterer(CatererDTO catererDTO)
    {
        if (catererDTO == null || catererDTO.getName() == null || catererDTO.getName().isBlank()
                || catererDTO.getAddress() == null || catererDTO.getContact() == null || catererDTO.getCapacity() == null)
            throw new MandatoryDataMissingException("Mandatory data is not provided in input");
    }

    private Caterer findById(String id)
    {
        return catererRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Caterer not found with id " + id));
    }
}
