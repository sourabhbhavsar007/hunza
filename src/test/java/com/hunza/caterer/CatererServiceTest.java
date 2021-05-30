package com.hunza.caterer;

import com.hunza.caterer.domain.Caterer;
import com.hunza.caterer.dto.*;
import com.hunza.caterer.exception.MandatoryDataMissingException;
import com.hunza.caterer.exception.NotFoundException;
import com.hunza.caterer.repository.CatererRepository;
import com.hunza.caterer.repository.KafkaRepository;
import com.hunza.caterer.service.CatererService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CatererServiceTest
{
    private static final String TEST_CATERER_ID = "1";
    private static final String TEST_CATERER_NAME = "Test Caterer name";
    private static final String TEST_CATERER_ADDRESS_CITY = "Test City";

    @Mock
    private CatererRepository catererRepository;

    @Mock
    private KafkaRepository kafkaRepository;

    private CatererService catererService;

    private CatererDTO catererDTO;
    private Caterer caterer;
    List<Caterer> caterers = new ArrayList<>();

    @BeforeEach
    void setUp()
    {
        catererService = new CatererService(catererRepository, kafkaRepository);

        setUpData();

        when(catererRepository.insert(caterer)).thenAnswer(//
                (Answer<Caterer>) invocation ->
                {
                    Object[] args = invocation.getArguments();
                    return (Caterer) args[0];
                });

        when(catererRepository.findById(TEST_CATERER_ID)).thenReturn(Optional.of(caterer));
        when(catererRepository.findByName(TEST_CATERER_NAME)).thenReturn(caterers);
    }

    private void setUpData()
    {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(TEST_CATERER_ADDRESS_CITY);
        addressDTO.setStreetName("John Street");
        addressDTO.setStreetNumber(55);
        addressDTO.setPostalCode("SO14 3HW");

        CapacityDTO capacityDTO = new CapacityDTO();
        capacityDTO.setMin(50);
        capacityDTO.setMax(200);

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setPhoneNumber("+0000000000");
        contactDTO.setMobileNumber("+1000000000");
        contactDTO.setEmailAddress("test@test.com");

        catererDTO = new CatererDTO();
        catererDTO.setId(TEST_CATERER_ID);
        catererDTO.setName(TEST_CATERER_NAME);
        catererDTO.setAddress(addressDTO);
        catererDTO.setCapacity(capacityDTO);
        catererDTO.setContact(contactDTO);

        caterer = new Caterer();
        catererDTO.fillEntity(caterer);
        caterers.add(caterer);
    }

    @Test
    void testMockCreation()
    {
        assertNotNull(catererRepository);
        assertNotNull(kafkaRepository);
        assertNotNull(catererService);
    }

    @Disabled
    @Test
    void testCreate()
    {
        CatererDTO catererResponseDTO = catererService.create(catererDTO);

        assertAll(//
                () -> assertNotNull(catererResponseDTO), //
                () -> assertEquals(TEST_CATERER_ID, catererResponseDTO.getId()));
    }

    @Test
    void testCreateFail()
    {
        catererDTO.setName(null);
        assertThrows(MandatoryDataMissingException.class, () -> catererService.create(catererDTO));
    }

    @Test
    void testGet()
    {
        CatererDTO catererDTO = catererService.get(TEST_CATERER_ID);

        assertAll( //
                () -> assertNotNull(catererDTO), //
                () -> assertEquals(TEST_CATERER_ID, catererDTO.getId()));
    }

    @Test
    void testGetFail()
    {
        when(catererRepository.findById(TEST_CATERER_ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> catererService.get(TEST_CATERER_ID));
    }

    @Test
    void testGetByName()
    {
        List<CatererDTO> catererDTOS = catererService.getByName(TEST_CATERER_NAME);

        assertAll( //
                () -> assertEquals(1, catererDTOS.size()), //
                () -> assertEquals(TEST_CATERER_ID, catererDTOS.get(0).getId()));
    }

    @Test
    void testListByCity()
    {
        when(catererRepository.findByAddress_City(any(), any())).thenReturn(new PageImpl<>(caterers));
        CatererDTOList catererDTOList = catererService.listByCity(TEST_CATERER_ADDRESS_CITY, PageRequest.of(0, 10));
        assertTrue(catererDTOList.getCatererDTOS().contains(catererDTO));
    }
}
