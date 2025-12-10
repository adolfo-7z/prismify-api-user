package prismify.auth.infrastructure.controllers.dto.responses;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PageMeta {

    private PageMeta() {
    }

    public static Map<String, Object> of(int page, int size, long totalElements, int totalPages) {
        Map<String, Object> meta = new LinkedHashMap<>();
        meta.put("page", page);
        meta.put("size", size);
        meta.put("totalPages", totalPages);
        meta.put("totalElements", totalElements);
        return meta;
    }
}
