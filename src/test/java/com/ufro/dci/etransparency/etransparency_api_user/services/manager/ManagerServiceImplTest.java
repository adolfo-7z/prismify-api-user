package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerServiceImpl service;

    private Manager activeManager;
    private Manager inactiveManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activeManager = new Manager();
        activeManager.setId(1L);
        activeManager.setUsername("activeManager");
        activeManager.setEmail("active@example.com");
        activeManager.setActive(true);
        inactiveManager = new Manager();
        inactiveManager.setId(2L);
        inactiveManager.setUsername("inactiveManager");
        inactiveManager.setEmail("inactive@example.com");
        inactiveManager.setActive(false);
    }

    @Test
    void testGetAllManagersSuccess() {
        Page<Manager> managerPage = new PageImpl<>(List.of(activeManager, inactiveManager));
        when(managerRepository.findAll(any(PageRequest.class))).thenReturn(managerPage);
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(activeManager, ManagerDTO.class))
                    .thenReturn(new ManagerDTO());
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) service.getAllManagers(0, 10);
            assertNotNull(result);
            assertEquals(1, ((List<?>) result.get("managers")).size());
            assertEquals(1, result.get("totalPages"));
        }
    }

    @Test
    void testGetAllManagersWithEmptyResult() {
        Page<Manager> emptyPage = new PageImpl<>(Collections.emptyList());
        when(managerRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) service.getAllManagers(0, 10);
        assertNotNull(result);
        assertTrue(((List<?>) result.get("managers")).isEmpty());
        assertEquals(1, result.get("totalPages"));
    }
}
