package br.com.lucas.tabelafipe.principal;

import br.com.lucas.tabelafipe.model.Dados;
import br.com.lucas.tabelafipe.model.Modelos;
import br.com.lucas.tabelafipe.service.ConsumoApi;
import br.com.lucas.tabelafipe.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();


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
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite o código da marca desejada: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos disponíveis: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);


    }
}