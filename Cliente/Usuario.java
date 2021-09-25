package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Usuario {

  public void enviarMensagem() {

  }

  public static void main(String[] args) throws IOException {

    int portaUsuario = (int) (Math.random() * 65534);

    // Porta do Usuário
    DatagramSocket socketRecebedorUsuario = new DatagramSocket(portaUsuario);

    // Receber e enviar o nome do remetente
    Scanner leitor = new Scanner(System.in);
    System.out.println("Digite o seu nome:");
    String texto = leitor.nextLine();
    leitor.nextLine();

    byte[] msg = texto.getBytes();
    InetAddress destino = InetAddress.getByName("localhost");
    DatagramPacket pacote = new DatagramPacket(msg, msg.length, destino, 13579);
    DatagramSocket socketEnviador = new DatagramSocket();
    System.out.println("PACOTE ENVIADO: " + new String(pacote.getData()));
    socketEnviador.send(pacote);

    // Enviar a porta do usuário
    msg = Integer.toString(portaUsuario).getBytes();
    destino = InetAddress.getByName("localhost");
    pacote = new DatagramPacket(msg, msg.length, destino, 13579);
    System.out.println("PACOTE ENVIADO: " + new String(pacote.getData()));
    socketEnviador.send(pacote);

    while (true) {
      System.out.println("Digite 1 para enviar e 2 para receber uma mensagem");
      int resposta = leitor.nextInt();
      leitor.nextLine();

      // Enviar a resposta do usuário
      msg = Integer.toString(resposta).getBytes();
      destino = InetAddress.getByName("localhost");
      pacote = new DatagramPacket(msg, msg.length, destino, 13579);
      System.out.println("PACOTE ENVIADO: " + new String(pacote.getData()));
      socketEnviador.send(pacote);

      if (resposta == 1) {
        // Receber e enviar o nome do destinatário
        System.out.println("Digite o nome do usuário destinatario:");
        texto = leitor.nextLine();
        leitor.nextLine();

        msg = texto.getBytes();
        destino = InetAddress.getByName("localhost");
        pacote = new DatagramPacket(msg, msg.length, destino, 13579);
        socketEnviador.send(pacote);

        // Receber e enviar a mensagem
        System.out.println("Digite a mensagem que deseja passar:");
        texto = leitor.nextLine();
        leitor.nextLine();

        msg = texto.getBytes();
        destino = InetAddress.getByName("localhost");
        pacote = new DatagramPacket(msg, msg.length, destino, 13579);
        socketEnviador.send(pacote);

      }
      if (resposta == 2) {
        socketRecebedorUsuario.receive(pacote);
        System.out.println("Mensagem recebida: " + new String(pacote.getData()));
      }

    }

  }

}
