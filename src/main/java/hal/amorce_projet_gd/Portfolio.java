package hal.amorce_projet_gd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private Map<String, Double> assets; // maps crypto ID to quantity owned

    public Portfolio() {
        this.assets = new HashMap<>();
    }

    public Portfolio(Map<String, Double> initialAssets) {
        this.assets = new HashMap<>(initialAssets);
    }

    public void addToPortfolio(String cryptoId, double quantity) {
        assets.put(cryptoId, assets.getOrDefault(cryptoId, 0.0) + quantity);
    }

    public void removeQuantity(String cryptoId, double quantity) {
        double currentQty = assets.getOrDefault(cryptoId, 0.0);
        double newQty = Math.max(0, currentQty - quantity);
        if (newQty == 0) {
            assets.remove(cryptoId);
        } else {
            assets.put(cryptoId, newQty);
        }
    }

    public Map<String, Double> getAssets() {
        return assets;
    }

    public List<CryptoAsset> getAssetsList() {
        List<CryptoAsset> assetList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : assets.entrySet()) {
            assetList.add(new CryptoAsset(entry.getKey(), entry.getValue()));
        }
        return assetList;
    }
    public List<String> getOwnedCryptoNames() {
        return new ArrayList<>(assets.keySet());
    }
    public CryptoAsset getAsset(String cryptoId) {
        Double quantity = assets.get(cryptoId);
        if (quantity != null) {
            return new CryptoAsset(cryptoId, quantity);
        } else {
            return null; // or you might want to handle this case differently
        }
    }
    // Other necessary methods
}
