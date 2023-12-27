package hal.amorce_projet_gd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRates {

    @JsonProperty("rates")
    private Map<String, Rate> rates;

    // Getter and Setter
    public Map<String, Rate> getRates() {
        return rates;
    }

    public void setRates(Map<String, Rate> rates) {
        this.rates = rates;
    }

    // Inner class to represent individual Rate details
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rate {

        @JsonProperty("name")
        private String name;

        @JsonProperty("unit")
        private String unit;

        @JsonProperty("value")
        private Double value;

        @JsonProperty("type")
        private String type;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
