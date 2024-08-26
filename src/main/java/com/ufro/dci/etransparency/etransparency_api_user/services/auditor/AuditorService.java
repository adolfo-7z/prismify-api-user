package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

public interface AuditorService {

    public Object getAllAuditors(int page, int size, String sortDirection, String name);

    public Object getSurveyResults(Long processId);
    
}