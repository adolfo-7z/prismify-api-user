package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;
/*package com.ufro.dci.transparency.transparency_api.services.auditor;

import com.ufro.dci.transparency.transparency_api.models.auditor.Auditor;
import com.ufro.dci.transparency.transparency_api.repositories.auditor.AuditorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditorServiceImplTest {

    @Mock
    private AuditorRepository auditorRepository;

    @InjectMocks
    private AuditorServiceImpl auditorService;

    private Auditor auditor;

    @BeforeEach
    void setUp() {
        auditor = new Auditor();
        auditor.setActive(true);
    }

    @Test
    void getAllAuditors_ShouldReturnActiveAuditors() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Auditor> auditorPage = new PageImpl<>(List.of(auditor));
        when(auditorRepository.findAll(pageable)).thenReturn(auditorPage);

        Map<String, Object> response = (Map<String, Object>) auditorService.getAllAuditors(0, 10, "asc", "id");

        assertNotNull(response);
        assertEquals(1, ((List<?>) response.get("auditors")).size());
        assertEquals(1, response.get("totalPages"));
    }
}*/
