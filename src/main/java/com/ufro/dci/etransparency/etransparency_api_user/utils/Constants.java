package com.ufro.dci.etransparency.etransparency_api_user.utils;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    // RECURSOS
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
    public static final String THE_INSTITUTION_REQUEST_WITH_ID = "TThe institution request with ID ";
    public static final String CAN_NOT_ACCEPT = "Can not accept process with ID ";
    public static final String CAN_NOT_REJECT = "Can not reject process with ID ";
    public static final String NO_MATURITY_MODEL_FOR_PROCESS = "No maturity model found for process ";
    public static final String NO_DIMENSIONS_MODEL_FOR_PROCESS = "No dimensions found in the maturity model for process ";
    public static final String NO_RESULTS_FOR_PROCESS = "No process result found for process ";
    public static final String NO_LEVEL_AVERAGES_FOR_PROCESS = "No average levels found for process ";
    public static final String THE_EVIDENCE_WITH_ID = "The evidence with ID ";
    public static final String RESOURCE_CONVERSION = "The conversion of the resource failed";

    // CONFLICTOS
    public static final String THE_RESOURCE_WAS_NOT_FOUND = "The resource was not found";
    public static final String INVALID_ACTION_CONDITION = "Invalid action";
    public static final String PROCESS_ALREADY_EXISTS = "A process already exists.";
    public static final String WAS_NOT_FOUND = " was not found.";
    public static final String WAS_NOT_FOUND_OR_INACTIVE = " was not found or is inactive.";
    public static final String RESOURCES_NOT_FOUND = "The resources were not found";
    public static final String WAS_REJECTED = " was rejected";
    public static final String RESOURCE_ALREADY_ASSIGNED = "The current resource is already assigned.";
    public static final String INVALID_PROCESS_STATUS = "Process status invalid for this operation";
    public static final String MODEL_STATUS_INVALID = "Model status invalid for this operation";
    public static final String PROCESS_REJECTED = "Process Rejected";
    public static final String INVALID_REJECTION_CONDITION = "Invalid rejection condition";
    public static final String INVALID_ACCEPTING_CONDITION = "Invalid accepting condition";
    public static final String EVIDENCE_NOT_REJECTED = "Evidence is not in rejected status and cannot be appealed";
    public static final String PROCESS_NOT_AUDIT = "This process is not being audited";

    // HITOS
    public static final String PROCESS_REQUESTED = "Se ha solicitado proceso";
    public static final String REQUEST_ACCEPTED = "Proceso fue aceptado";
    public static final String REQUEST_REJECTED = "Proceso fue rechazado";
    public static final String SURVEY_FINISHED1 = "Ha concluido proceso de encuesta con un";
    public static final String SURVEY_FINISHED2 = "% de respuestas";
    public static final String AUDIT_STARTED = "Se ha iniciado auditoría del proceso";
    public static final String PROCESS_SURVEY_REJECTED = "Se ha rechazado el proceso";
    public static final String EVIDENCE_APPEALED = "Se apeló el rechazo";
    public static final String PROCESS_FINISHED = "Proceso finalizado";

    // HTTP
    public static final String NOT_FOUND = "Not found";
    public static final String OPERATION_FAILED = "Operation failed";
    public static final String OPERATION_SUCCESSFUL = "Operation successful";

    // ARCHIVOS
    public static final String FILE_NOT_NULL = "File must not be null or empty";
    public static final String FILE_SIZE_EXCEEDED = "File size exceeds the maximum allowed size of ";
    public static final String FILE_TYPE_NOT_SUPPORTED = "File type is not supported";
    public static final String FILE_CAPACITY_EXCEEDED = "Cannot upload more than 3 files per dimension for this process";
    public static final String FILE_PROCESSING_FAILED = "File processing operation failed";
    public static final String FILE_COLUMNS_NOT_FOUND = "Required columns not found in the file";
    public static final long MAX_FILE_SIZE = (long) 50 * 1024 * 1024;
    public static final long MAX_REQUEST_SIZE = (long) 55 * 1024 * 1024;

    // MAIL
    public static final String FAILED_EMAIL = "Failed to send email to the recipient";

    public static final String RECOVERY_CODE_MAIL = """
            Estimado(a) %s,

            Su código de recuperación es: %s

            Este código expirará en 15 minutos.

            Atentamente,
            Equipo de e-Transparencia
            """;

    // RESPUESTAS
    public static final double APPROVAL_PERCENTAGE = 0.75;

}
