package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;

public interface AuditorCrudService {

    public AuditorDTO getAuditor(Long auditorId);

    public AuditorDTO createAuditor(AuditorDTO auditorDTO);

    public AuditorDTO updateAuditor(Long auditorId, AuditorUpdateDTO auditorUpdateDTO);

    public AuditorDTO toggleAuditorStatus(Long auditorId);
    
}
