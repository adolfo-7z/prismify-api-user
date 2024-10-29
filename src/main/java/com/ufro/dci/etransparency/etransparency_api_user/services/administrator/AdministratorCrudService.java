package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;

public interface AdministratorCrudService {

    public AdministratorDTO getAdministrator(Long administratorId);

    public AdministratorDTO createAdministrator(AdministratorDTO administratorDTO);

    public AdministratorDTO updateAdministrator(Long administratorId, AdministratorUpdateDTO administratorUpdateDTO);

    public AdministratorDTO toggleAdministratorStatus(Long administratorId);

}
