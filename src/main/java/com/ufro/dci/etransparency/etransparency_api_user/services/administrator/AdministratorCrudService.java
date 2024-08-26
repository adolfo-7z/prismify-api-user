package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;

public interface AdministratorCrudService {

    public AdministratorDTO getAdministrator(Long adminId);

    public AdministratorDTO createAdministrator(AdministratorDTO administratorDTO);

    public AdministratorDTO updateAdministrator(Long adminId, AdministratorUpdateDTO administratorUpdateDTO);

    public AdministratorDTO toggleAdministratorStatus(Long adminId);

}
