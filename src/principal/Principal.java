package principal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.ApiConnector;
import modelos.Conversion;
import modelos.RespuestaCodigo;

import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        ApiConnector apiConnector = new ApiConnector();
        Gson gson= new GsonBuilder().setPrettyPrinting().create();

        try {
            String codeResponse = apiConnector.getCurrencyCodes();
            RespuestaCodigo respuestaCodigo = gson.fromJson(codeResponse, RespuestaCodigo.class);
            if (respuestaCodigo.getSupportedCodes() != null) {
                new ConvertirMoneda(respuestaCodigo.getSupportedCodes());
            } else {
                System.out.println("Error: la lista de monedas es nula.");
            }
        } catch (IOException| InterruptedException e){
            e.printStackTrace();

        }
    }
}
