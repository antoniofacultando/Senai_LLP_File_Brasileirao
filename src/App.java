import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class App {

    // Classe que representa um time
    static class Time {

        String nome;
        int pontos;
        int golsFeitos;
        int golsSofridos;

        public Time(String nome) {
            this.nome = nome;
        }

        // Calcula saldo de gols
        public int getSaldo() {
            return golsFeitos - golsSofridos;
        }
    }

    public static void main(String[] args) {

        // Mapa para armazenar os times
        HashMap<String, Time> tabela = new HashMap<>();

        try {

            // Abre o arquivo
            BufferedReader aral =
                    new BufferedReader(
                            new FileReader("jogos.txt"));

            String linha;

            // Lê linha por linha
            while ((linha = aral.readLine()) != null) {

                String[] dados = linha.split(",");

                String timeA = dados[1];
                String timeB = dados[2];
                String resultado = dados[3];

                String[] gols = resultado.split("x");

                int golsA = Integer.parseInt(gols[0]);
                int golsB = Integer.parseInt(gols[1]);

                // Cria os times caso não existam
                tabela.putIfAbsent(timeA, new Time(timeA));
                tabela.putIfAbsent(timeB, new Time(timeB));

                Time A = tabela.get(timeA);
                Time B = tabela.get(timeB);

                // Atualiza gols feitos e sofridos
                A.golsFeitos += golsA;
                A.golsSofridos += golsB;

                B.golsFeitos += golsB;
                B.golsSofridos += golsA;

                // Vitória do time A
                if (golsA > golsB) {

                    A.pontos += 3;

                }
                // Vitória do time B
                else if (golsB > golsA) {

                    B.pontos += 3;

                }
                // Empate
                else {

                    A.pontos += 1;
                    B.pontos += 1;
                }
            }

        } catch (IOException e) {

            System.out.println("Erro ao ler arquivo.");
            return;
        }

        // Converte o HashMap para lista
        ArrayList<Time> classificacao =
                new ArrayList<>(tabela.values());

        // Ordena por pontos e saldo
        classificacao.sort((t1, t2) -> {

            if (t2.pontos != t1.pontos) {

                return t2.pontos - t1.pontos;
            }

            return t2.getSaldo() - t1.getSaldo();
        });

        // Exibe classificação
        System.out.println("+----+----------------+--------+-------+");
        System.out.println("| #  | Time           | Pontos | Saldo |");
        System.out.println("+----+----------------+--------+-------+");

        for (int i = 0; i < classificacao.size(); i++) {

            Time time = classificacao.get(i);

            System.out.printf(
                    "| %-2d | %-14s | %-6d | %-5d |\n",
                    (i + 1),
                    time.nome,
                    time.pontos,
                    time.getSaldo()
            );
        }

        System.out.println("+----+----------------+--------+-------+");
    }
}