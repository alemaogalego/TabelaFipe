package br.com.lucas.tabelafipe.principal;

import br.com.lucas.tabelafipe.service.ConsumoApi;

import java.util.Scanner;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();


    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        var menu = """
                *** OPÇÕES ***
                1- Carro
                2- Moto
                3- Caminhão
                
                Digite uma das opções em numeros para consulta:
                
                """;
        System.out.println(menu);

        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.equals("1")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.equals("2")) {
            endereco = URL_BASE + "motos/marcas";
        } else if (opcao.equals("3")) {
            endereco = URL_BASE + "caminhoes/marcas";
        } else {
            System.out.println("Opção inválida!");
            return;
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);

    }
}