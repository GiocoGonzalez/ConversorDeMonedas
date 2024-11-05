package modelos;

import java.util.ArrayList;
import java.util.List;

public class RespuestaCodigo {
    private String resultado;
    private List <List<String>>supported_codes;

    public String getResultado(){
        return resultado;
    }

    public List<Moneda> getSupportedCodes(){
        List<Moneda> monedas = new ArrayList<>();
        for (List<String> code :supported_codes){
            monedas.add(new Moneda(code.get(0), code.get(1)));
        }
        return monedas;
    }
}
