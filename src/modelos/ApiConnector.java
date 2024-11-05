package modelos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConnector {
   private static final String BASE_URL="https://v6.exchangerate-api.com/v6/098a67b12c0cc622bf132a23";

   public String getCurrencyCodes() throws IOException, InterruptedException {
       String url= BASE_URL+ "codes";
       return sendRequest(url);
   }
    public String getConversionRates(String currencyCode) throws IOException, InterruptedException {
        String url = BASE_URL + "latest/" + currencyCode; // URL para obtener tasas de conversión
        return sendRequest(url);
    }

    private String sendRequest(String url) throws IOException, InterruptedException {
       HttpClient cliente= HttpClient.newHttpClient();
       HttpRequest request= HttpRequest.newBuilder()
               .uri(URI.create(url))
               .build();

       HttpResponse<String> response= cliente.send(request, HttpResponse.BodyHandlers.ofString());
       return response.body();
   }
}
