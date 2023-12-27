
        package hal.amorce_projet_gd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import javafx.scene.Node;

public class PortefeuilleController {

    @FXML
    private LineChart<String, Number> lineChart;
    private final CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
    private final String[] months = {
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    };
    private final Color[] colors = {
            Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE, Color.PINK,
            Color.PURPLE, Color.AQUA, Color.BROWN, Color.CHOCOLATE, Color.CORAL, Color.DARKBLUE
    };
    private final Random random = new Random();

    @FXML
    private void initialize() {
        lineChart.setLegendVisible(false); // Disable the legend
        generateContinuousColoredDataForYear();
        addCurrentBitcoinValue();
    }


    private void generateContinuousColoredDataForYear() {
        double previousValue = 30000; // starting value for the first point
        LocalDate today = LocalDate.now();
        int todayMonthIndex = today.getMonthValue() - 1;
        for (int i = 0; i < months.length; i++) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(months[i]);
            int daysInMonth = (i == todayMonthIndex) ? today.getDayOfMonth() : 30; // Up to today or full month
            for (int j = 0; j < daysInMonth; j++) {
                double fakeValue = previousValue + (random.nextDouble() * 2000 - 1000);
                XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(String.valueOf(j + 1) + " " + months[i], fakeValue);
                // Set each data point's node to be invisible
                Node invisibleNode = new Circle(0);
                invisibleNode.setVisible(false);
                dataPoint.setNode(invisibleNode);
                series.getData().add(dataPoint);
                previousValue = fakeValue;
            }
            lineChart.getData().add(series);
        }
        Platform.runLater(this::applySeriesStyles);
    }

    private void applySeriesStyles() {
        for (int i = 0; i < lineChart.getData().size(); i++) {
            XYChart.Series<String, Number> series = lineChart.getData().get(i);
            Color color = colors[i % colors.length]; // Ensure index is within bounds
            String rgb = toRGBCode(color);
            series.getNode().setStyle("-fx-stroke: " + rgb + "; -fx-stroke-width: 2px;");
        }
    }

    private void addCurrentBitcoinValue() {
        try {
            Map<String, Map<String, Double>> prices = client.getPrice("bitcoin", "usd");
            Double currentPrice = prices.get("bitcoin").get("usd");
            LocalDate today = LocalDate.now();
            int monthIndex = today.getMonthValue() - 1;
            if(monthIndex >= 0 && monthIndex < months.length) {
                String currentDate = today.getDayOfMonth() + " " + months[monthIndex];
                XYChart.Series<String, Number> series = lineChart.getData().get(monthIndex);
                XYChart.Data<String, Number> todayData = new XYChart.Data<>(currentDate, currentPrice);
                Circle todayNode = new Circle(5, Color.GOLD); // Distinctive marker
                todayData.setNode(todayNode);
                series.getData().add(todayData); // Add the current price as the last data point
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}

