# Tabela FIPE

Aplicação **Java 17 / Spring Boot 4.1.1** de linha de comando (console) que consulta a
**API pública da Tabela FIPE** (`https://parallelum.com.br/fipe/api/v1/`) para buscar
preços de veículos. É um projeto de curso, focado em consumo de API HTTP e
desserialização de JSON.

## Funcionalidades

O fluxo é interativo, conduzido pelo método `Main.exibeMenu`:

1. Usuário escolhe o tipo de veículo: **1 - Carro**, **2 - Moto**, **3 - Caminhão**.
2. O app lista as **marcas** disponíveis (ordenadas por código); o usuário digita o
   código da marca.
3. O app lista os **modelos** da marca; o usuário digita um trecho do nome para filtrar
   a lista.
4. O usuário digita o código do modelo desejado.
5. O app busca todos os **anos** disponíveis e, para cada ano, consulta o **valor de
   avaliação**, exibindo a lista final de veículos com preço, marca, modelo, ano e
   combustível.

Toda a saída é feita via `System.out.println`.

## Estrutura do código

| Pacote      | Classe                          | Responsabilidade                                                                 |
|-------------|---------------------------------|---------------------------------------------------------------------------------|
| (raiz)      | `TabelafipeApplication`         | Bootstrap Spring Boot; implementa `CommandLineRunner` e chama `Main`.          |
| `principal` | `Main`                          | Menu interativo (`Scanner`), orquestra as chamadas e usa Streams para ordenar/filtrar. |
| `service`   | `ConsumoApi`                    | Faz requisições HTTP com `java.net.http.HttpClient`.                            |
| `service`   | `ConverteDados` / `IConverteDados` | Converte JSON em objeto ou lista via Jackson `ObjectMapper`.                 |
| `model`     | `Dados` (record)               | `codigo`, `nome` — usado para marcas, modelos e anos.                          |
| `model`     | `Modelos` (record)             | Wrapper com `List<Dados> modelos`.                                             |
| `model`     | `Veiculo` (record)             | `valor`, `marca`, `modelo`, `anoModelo`, `combustivel` (mapeados com `@JsonAlias`). |

## Dependências

- `spring-boot-starter` (sem web — roda como aplicação de console)
- `jackson-databind` 2.17.0 (parsing de JSON)
- `spring-boot-starter-test`

## Como executar

Pré-requisitos: **JDK 17** e acesso à internet.

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

Ou gere o JAR e execute:

```bash
./mvnw clean package
java -jar target/tabelafipe-0.0.1-SNAPSHOT.jar
```

## Observações

- Não há camada web/REST nem persistência; a aplicação roda inteiramente no console.
- Tratamento de erros básico (exceções encapsuladas em `RuntimeException`).
- O `import java.security.Principal` em `TabelafipeApplication` não é utilizado (resquício).
- Sem testes reais além do `contextLoads` padrão gerado pelo Spring Initializr.
