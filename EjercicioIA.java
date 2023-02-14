import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    
    private List<String> baraja;
    private List<String> manoJugador;
    private List<String> manoCasa;
    private Random aleatorio;
    private Scanner scanner;
    private int saldoJugador;
    
    public Blackjack() {
        this.baraja = new ArrayList<>();
        this.manoJugador = new ArrayList<>();
        this.manoCasa = new ArrayList<>();
        this.aleatorio = new Random();
        this.scanner = new Scanner(System.in);
        this.saldoJugador = 100;
    }
    
    public void jugar() {
        System.out.println("¡Bienvenido al juego de Blackjack!");
        while (saldoJugador > 0) {
            System.out.println("Saldo actual: " + saldoJugador);
            System.out.println("¿Cuánto desea apostar?");
            int apuesta = scanner.nextInt();
            if (apuesta > saldoJugador) {
                System.out.println("No tienes suficiente saldo para esa apuesta.");
                continue;
            }
            inicializarBaraja();
            inicializarManos();
            while (true) {
                mostrarManoJugador();
                int totalJugador = calcularTotalMano(manoJugador);
                if (totalJugador > 21) {
                    System.out.println("¡Te has pasado de 21! Has perdido.");
                    saldoJugador -= apuesta;
                    break;
                } else if (totalJugador == 21) {
                    System.out.println("¡Blackjack! ¡Has ganado!");
                    saldoJugador += apuesta;
                    break;
                }
                System.out.println("¿Quieres pedir otra carta? (s/n)");
                String respuesta = scanner.next();
                if (respuesta.equalsIgnoreCase("s")) {
                    robarCartaJugador();
                } else {
                    mostrarManoCasa();
                    int totalCasa = calcularTotalMano(manoCasa);
                    while (totalCasa < 17) {
                        robarCartaCasa();
                        totalCasa = calcularTotalMano(manoCasa);
                    }
                    mostrarManoCasa();
                    if (totalCasa > 21) {
                        System.out.println("La casa se ha pasado de 21. ¡Has ganado!");
                        saldoJugador += apuesta;
                    } else if (totalCasa == totalJugador) {
                        System.out.println("¡Empate!");
                    } else if (totalCasa > totalJugador) {
                        System.out.println("La casa ha ganado.");
                        saldoJugador -= apuesta;
                    } else {
                        System.out.println("¡Has ganado!");
                        saldoJugador += apuesta;
                    }
                    break;
                }
            }
        }
        System.out.println("Te has quedado sin saldo. ¡Fin del juego!");
    }
    
    private void inicializarBaraja() {
        baraja.clear();
        String[] palos = {"♥", "♦", "♣", "♠"};
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String palo : palos) {
            for (String valor : valores) {
                baraja.add(valor + palo);
            }
        }
    }
    
    private void inicializarManos() {
        manoJugador.clear();
        manoCasa.clear();
