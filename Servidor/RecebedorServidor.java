package Servidor;

import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RecebedorServidor {

  static List<String> listaNomesConectados = new ArrayList<String>();
  static List<String> listaPortasConectadas = new ArrayList<String>();

  public static void gerenciarNomesPortas(String nome, String porta) {
    boolean jaConectou = false;

    for (int i = 0; i < listaNomesConectados.size(); i++) {
      if (listaNomesConectados.get(i) == nome) {
        jaConectou = true;
      }
    }

    if (!jaConectou) {
      listaNomesConectados.add(nome);
      listaPortasConectadas.add(porta);
    }
  }

  public static int destinatarioConectado(String nome) {
    int portaConectada = -1;

    for (int i = 0; i < listaNomesConectados.size(); i++) {
      if (listaNomesConectados.get(i) == nome) {
        portaConectada = Integer.parseInt(listaPortasConectadas.get(i));
      }
    }

    return portaConectada;
  }

  public static void main(String[] args) throws IOException {

    byte[] msg = new byte[256];

    DatagramPacket pacote = new DatagramPacket(msg, msg.length);

    DatagramSocket socketRecebedor = new DatagramSocket(13579);

    while (true) {
      // Receber nome remetente
      socketRecebedor.receive(pacote);

      String nomeRemetente = new String(pacote.getData());
      System.out.println("Nome remetente: " + nomeRemetente);

      // Receber a porta
      socketRecebedor.receive(pacote);
      String portaDoUsuario = new String(pacote.getData());
      System.out.println("Porta: " + portaDoUsuario);
      gerenciarNomesPortas(nomeRemetente, portaDoUsuario);

      // Receber a ação do usuário
      socketRecebedor.receive(pacote);
      System.out.println("INT DANDO ERRO: " + new String(pacote.getData()));
      Integer acaoDoUsuario = Integer.parseInt(new String(pacote.getData()));
      if (acaoDoUsuario == 1) {
        System.out.println("Usuario deseja enviar mensagem: ");

        // Receber nome destinatário
        socketRecebedor.receive(pacote);
        String nomeDestinatario = new String(pacote.getData());
        System.out.println("Nome destinatario: " + nomeDestinatario);

        // Receber a mensagem
        socketRecebedor.receive(pacote);
        String mensagemDoUsuario = new String(pacote.getData());
        System.out.println("Mensagem: " + mensagemDoUsuario);

        int portaDestinatario = destinatarioConectado(nomeDestinatario);
        if (portaDestinatario != -1) {

          // Enviar mensagem pro usuário destinatário
          InetAddress destino = InetAddress.getByName("localhost");
          msg = mensagemDoUsuario.getBytes();
          pacote = new DatagramPacket(msg, msg.length, destino, portaDestinatario);
          DatagramSocket socketEnviador = new DatagramSocket();
          socketEnviador.send(pacote);

        } else {
          System.out.println("Destinatario Não Conectado!");
        }
      }
      if (acaoDoUsuario == 2) {
        System.out.println("Usuario deseja receber mensagem: ");
      }

    }

  }

}