package com.back.tpi.rutas_tramos.rutas_tramos.dto;

import java.util.List;
import lombok.Data;

@Data
public class DistanceResponse {
    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<Row> rows;
    private String status;

    @Data
    public static class Row {
        private List<Element> elements;
    }

    @Data
    public static class Element {
        private Distance distance;
        private Duration duration;
        private String status;
    }

    @Data
    public static class Distance {
        private String text;
        private long value;
    }

    @Data
    public static class Duration {
        private String text;
        private long value;
    }
}
