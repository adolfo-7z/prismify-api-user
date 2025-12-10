package prismify.email.application.support;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import prismify.email.infrastructure.controllers.exception.custom.EmailTemplateLoaderException;

/**
 * Clase utilitaria para cargar y acceder a plantillas de correo electrónico
 * definidas en un archivo YAML.
 * <p>
 * El archivo esperado se encuentra en el classpath bajo
 * "templates/body-templates.yaml" y debe tener la estructura:
 * 
 * <pre>
 * key:
 *   subject: "Asunto del correo"
 *   body: "Cuerpo del correo"
 * </pre>
 * 
 * Los métodos proporcionan acceso al asunto y cuerpo de las plantillas
 * mediante una clave única.
 * 
 * @author Adolfo Plaza
 */
public class EmailTemplateLoader {

    /**
     * Constructor privado para evitar la instanciación.
     * <p>
     * Como esta es una clase utilitaria, no debe ser instanciada.
     * Cualquier intento de hacerlo arrojará una
     * {@link UnsupportedOperationException}.
     */
    private EmailTemplateLoader() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static Map<String, Map<String, String>> templates;

    static {
        try (InputStream is = EmailTemplateLoader.class
                .getClassLoader()
                .getResourceAsStream("templates/body-templates.yaml")) {
            if (is == null) {
                throw new EmailTemplateLoaderException(
                        "Email template file not found in classpath: templates/body-templates.yaml");
            }
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            templates = mapper.readValue(is, new TypeReference<Map<String, Map<String, String>>>() {
            });
        } catch (Exception e) {
            throw new EmailTemplateLoaderException("Failed to load email templates");
        }
    }

    private static Map<String, String> templateFor(String key) {
        Map<String, String> t = templates.get(key);
        if (t == null)
            throw new EmailTemplateLoaderException("Unknown template key: " + key);
        return t;
    }

    /**
     * Obtiene el asunto de la plantilla de correo correspondiente a la clave
     * proporcionada.
     *
     * @param key clave única de la plantilla
     * @return el asunto del correo
     * @throws NullPointerException si la clave no existe o la plantilla es nula
     */
    public static String getSubject(String key) {
        return templateFor(key).getOrDefault("subject", "");
    }

    public static String getBody(String key) {
        return templateFor(key).getOrDefault("body", "");
    }

    /**
     * Obtiene el cuerpo de la plantilla de correo correspondiente a la clave
     * proporcionada.
     *
     * @param key clave única de la plantilla
     * @return el cuerpo del correo
     * @throws NullPointerException si la clave no existe o la plantilla es nula
     */
    public static String getBody(String key, Map<String, String> variables) {
        String body = getBody(key);
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            body = body.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return body;
    }

}
