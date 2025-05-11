package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerUpdateDTO;

public interface ManagerCrudService {

    public ManagerDTO getManager(Long managerId);

    public ManagerDTO createManager(ManagerDTO managerDTO);

    public ManagerDTO updateManager(Long managerId, ManagerUpdateDTO managerUpdateDTO);

    public ManagerDTO toggleManagerStatus(Long managerId);

}
