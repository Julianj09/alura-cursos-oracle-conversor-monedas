package main.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import main.exception.ConversorException;
import main.model.Money;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Conversor {

    private static final String FILE_NAME = "historial.json";

    private static final TypeAdapter<LocalDateTime> LOCAL_DATE_TIME_ADAPTER = new TypeAdapter<>() {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(value));
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDateTime.parse(in.nextString(), formatter);
        }
    };

    public static void ejecute() {
        List<Money> historial = cargarHistorial();
        Scanner lectura = new Scanner(System.in);
        boolean salida = false;

        System.out.println("-----------------------------------------------");
        System.out.println("Bienvenido/a al programa de conversor de Monedas \n Porfavor elige una de las opciones");

        try {
            while (!salida) {
                System.out.println("1) Ver el tablero de monedas disponibles");
                System.out.println("2) Convertir tu moneda");
                System.out.println("3) Historal de conversiones");
                System.out.println("4) Salir");
                try {
                    System.out.print("Elegí una opción: ");
                    int eleccion = lectura.nextInt();
                    lectura.nextLine();

                    switch (eleccion) {
                        case 1:
                            System.out.println("Tablero generado");
                            BusinesLogic.mostrarMap();
                            break;
                        case 2:
                            try {
                                System.out.println("Ingrese el nombre de tu moneda base");
                                String money_base = lectura.nextLine().trim().toUpperCase();

                                System.out.println("Ingresa el nombre de la moneda que quieras convertir");
                                String money_conversor = lectura.nextLine().trim().toUpperCase();

                                System.out.println("Ingrese el valor que deseas convertir");
                                int valor_join = Integer.parseInt(lectura.nextLine());

                                if (valor_join <= 0) {
                                    throw new ConversorException("El monto debe ser mayor que cero.");
                                }
                                Money result = BusinesLogic.conversor_money(money_base, money_conversor, valor_join);
                                if (result == null) {
                                    System.out.println("Países mal ingresados, por favor revise nuevamente el tablero");
                                } else {
                                    System.out.println(result);
                                    historial.add(result);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Error: Debés ingresar un número válido para el monto.");
                            } catch (Exception e) {
                                System.out.println(" Error inesperado: " + e.getMessage());
                            }
                            break;
                        case 3:
                            Money.listmoney(historial);
                            break;
                        case 4:
                            System.out.println("Saliendo del sistema \n Hasta pronto :)");
                            salida = true;
                            break;
                        default:
                            System.out.println("Opción no válida. Por favor, elige una opción entre 1 y 4.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Debés ingresar un número entero válido.");
                    lectura.nextLine();
                }
            }
        } finally {
            guardarHistorial(historial);
            lectura.close();
        }
    }

    private static List<Money> cargarHistorial() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_ADAPTER)
                .setPrettyPrinting()
                .create();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<Money>>() {}.getType();
            List<Money> historial = gson.fromJson(reader, listType);
            // Si el archivo existe pero está vacío o es nulo, se devuelve una lista vacía.
            return historial != null ? historial : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("No se encontró un historial anterior. Se creará uno nuevo.");
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.out.println("Error al leer el historial (archivo mal formado). Se creará uno nuevo.");
            return new ArrayList<>();
        }
    }

    private static void guardarHistorial(List<Money> historial) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_ADAPTER)
                .setPrettyPrinting()
                .create();
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(historial, writer);
            System.out.println("Historial de conversiones guardado exitosamente en " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }
}