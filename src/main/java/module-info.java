
module hal.amorce_projet_gd {

    requires javafx.controls;
    requires javafx.fxml;

// CoinGecko...
    requires okhttp3;
    requires retrofit2;
    requires retrofit2.converter.jackson;
    requires com.fasterxml.jackson.annotation;
    requires static lombok;
// ...CoinGecko

    opens com.litesoftwares.coingecko.domain to com.fasterxml.jackson.databind, retrofit2;
    opens hal.amorce_projet_gd to javafx.fxml;
    exports hal.amorce_projet_gd;
}