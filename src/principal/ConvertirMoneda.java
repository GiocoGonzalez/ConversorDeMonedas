package principal;

import com.google.gson.Gson;
import modelos.ApiConnector;
import modelos.Conversion;
import modelos.Moneda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ConvertirMoneda extends javax.swing.JFrame {
    private JComboBox<String>fromCurrency;
    private JComboBox<String>toCurrency;
    private JTextField amountInput;
    private JLabel resultLabel;
    private JButton convertirButton;


    public ConvertirMoneda(List<Moneda> monedas) {
        setTitle("Convertir Moneda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(3,2,10,10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panelInferior.setLayout( new FlowLayout());

        fromCurrency = new JComboBox<>();
        toCurrency = new JComboBox<>();
        amountInput= new JTextField();
        JTextField amountInput = new JTextField();
        resultLabel = new JLabel("Resultado: ");
        convertirButton= new JButton("Convertir");

        resultLabel.setFont(new Font("ARIEL", Font.BOLD, 16));
        convertirButton.setBackground(Color.RED);
        convertirButton.setForeground(Color.WHITE);

        for(Moneda moneda: monedas) {
            fromCurrency.addItem(moneda.codigo());
            toCurrency.addItem(moneda.codigo());
        }

        panelSuperior.add(new JLabel("Moneda a convertir:"));
        panelSuperior.add(fromCurrency);
        panelSuperior.add(new JLabel("¿A qué tipo de moneda lo quieres convertir?: "));
        panelSuperior.add(toCurrency);
        panelSuperior.add(new JLabel ("Cantidad a convertir: "));
        panelSuperior.add(amountInput);

        panelInferior.add(convertirButton);
        panelInferior.add(resultLabel);

        add(panelSuperior, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        convertirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConversion();

            }
        });
        setVisible(true);

    }

    private void realizarConversion(){
        try {
            String fromCurrencyCode = fromCurrency.getSelectedItem().toString().split(" ")[0];
            String toCurrencyCode = toCurrency.getSelectedItem().toString().split(" ")[0];
            double amount = Double.parseDouble(amountInput.getText());

            // Conexión con la API
            ApiConnector apiConnector = new ApiConnector();
            String conversionResponse = apiConnector.getConversionRates(fromCurrencyCode);

            double conversionRate = obtenerTasaDeConversion(conversionResponse, toCurrencyCode);

            // Mostrar el resultado
            double convertedAmount = amount * conversionRate;
            resultLabel.setText("Resultado: " + convertedAmount);

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            resultLabel.setText("Error: " + ex.getMessage());
        } catch (NumberFormatException e) {
            resultLabel.setText("Error, ingrese una cantidad válida");
        }
    }

    private double obtenerTasaDeConversion(String conversionResponse, String toCurrencyCode) {
        Gson gson = new Gson();
        Conversion respuesta = gson.fromJson(conversionResponse, Conversion.class);
        return respuesta.getConversion_rates().get(toCurrencyCode);
    }
}
