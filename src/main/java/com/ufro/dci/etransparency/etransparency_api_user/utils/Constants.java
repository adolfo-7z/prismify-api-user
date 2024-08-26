package com.ufro.dci.etransparency.etransparency_api_user.utils;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String THE_AUDITOR_WITH_ID = "The auditor with ID ";
    public static final String THE_DIMENSION_WITH_ID = "The dimension with ID ";
    public static final String THE_LEVEL_WITH_ID = "The level with ID ";
    public static final String THE_ADMIN_WITH_ID = "The admin with ID ";
    public static final String THE_USER_WITH_ID = "The user with ID ";
    public static final String THE_INSTITUTION_WITH_ID = "The institution with ID ";
    public static final String THE_MANAGER_WITH_ID = "The manager with ID ";
    public static final String THE_PROCESS_WITH_ID = "The process with ID ";
    public static final String THE_SURVEY_ID = "The survey with ID ";
    public static final String THE_MATURITY_MODEL_ID = "The maturity model with ID ";
    public static final String THE_INSTITUTION_REQUEST_WITH_ID  = "TThe institution request with ID ";
    public static final String CAN_NOT_ACCEPT = "Can not accept process with ID ";
    public static final String CAN_NOT_REJECT = "Can not reject process with ID ";
    public static final String NO_MATURITY_MODEL_FOR_PROCESS = "No maturity model found for process ";
    public static final String NO_DIMENSIONS_MODEL_FOR_PROCESS = "No dimensions found in the maturity model for process ";
    public static final String NO_RESULTS_FOR_PROCESS = "No process result found for process ";
    public static final String NO_LEVEL_AVERAGES_FOR_PROCESS = "No average levels found for process ";
    public static final String THE_EVIDENCE_WITH_ID = "The evidence with ID ";
    public static final String THE_RESOURCE_WAS_NOT_FOUND = "The resource was not found";
    public static final String INVALID_ACTION_CONDITION = "Invalid action";

    public static final String PROCESS_ALREADY_EXISTS = "A process already exists.";
    public static final String WAS_NOT_FOUND = " was not found.";
    public static final String WAS_NOT_FOUND_OR_INACTIVE = " was not found or is inactive.";
    public static final String WAS_REJECTED = " was rejected";
    public static final String RESOURCE_ALREADY_ASSIGNED = "The current resource is already assigned.";
    public static final String INVALID_PROCESS_STATUS = "Process status invalid for this operation";
    public static final String MODEL_STATUS_INVALID = "Model status invalid for this operation";
    public static final String PROCESS_REJECTED = "Process Rejected";
    public static final String INVALID_REJECTION_CONDITION = "Invalid rejection condition";
    public static final String INVALID_ACCEPTING_CONDITION = "Invalid accepting condition";
    

    public static final String NOT_FOUND = "NOT FOUND";
    public static final String OPERATION_FAILED = "Operation failed";
    public static final String OPERATION_SUCCESSFUL = "Operation successful";

    public static final String FILE_NOT_NULL = "File must not be null or empty";
    public static final String FILE_SIZE_EXCEEDED = "File size exceeds the maximum allowed size of ";
    public static final String FILE_TYPE_NOT_SUPPORTED = "File type is not supported";
    public static final String DIMENSION_CAPACITY_EXCEEDED = "Cannot upload more than 3 files per dimension for this process";
    public static final String FILE_PROCESSING_FAILED = "File processing operation failed";
    public static final long MAX_FILE_SIZE = 50 * 1024 * 1024;
    public static final long MAX_REQUEST_SIZE = 55 * 1024 * 1024;

}
