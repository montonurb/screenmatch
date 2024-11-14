package com.montonurb.screenmatch_backend.principal;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.montonurb.screenmatch_backend.enums.Categoria;
import com.montonurb.screenmatch_backend.model.DadosSerie;
import com.montonurb.screenmatch_backend.model.DadosTemporada;
import com.montonurb.screenmatch_backend.model.Episodio;
import com.montonurb.screenmatch_backend.model.Serie;
import com.montonurb.screenmatch_backend.repository.SerieRepository;
import com.montonurb.screenmatch_backend.service.ConsumoAPI;
import com.montonurb.screenmatch_backend.service.ConverteDados;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e3313c51";
    private final List<DadosSerie> dadosSeries = new ArrayList<>();
    private final SerieRepository serieRepository;
    private List<Serie> series = new ArrayList<>();
    Optional<Serie> serieBuscada;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }
    

    public void exibeMenu() throws UnsupportedEncodingException {
        var opcao = -1;
        var menu = """
            1 - Buscar Séries
            2 - Buscar Episódios
            3 - Listar Séries Buscadas
            4 - Buscar Série por Nome
            5 - Buscar Série por Ator
            6 - Buscar Série por Categoria
            7 - Buscar Série por Total de Temporadas e Avaliacao
            8 - Buscar Top 5
            9 - Buscar Episódios por Trecho do Título
            10 - Buscar Top 5 Episódios por Série
            11 - Buscar Episódios a partir de uma data

            0 - Sair
        """;

        while (opcao != 0) {
            switch (opcao) {
                case 1 -> buscarSerie();
                case 2 -> buscarEpisodioPorSerie();
                case 3 -> listarSeriesBuscadas();
                case 4 -> buscarSeriePorTitulo();
                case 5 -> buscarSeriePorAtor();
                case 6 -> buscarSeriesPorCategoria();
                case 7 -> buscarSeriesPorTemporada();
                case 8 -> buscarTopSeries();
                case 9 -> buscarEpisodioPorTrecho();
                case 10 -> topEpisodiosPorSerie();
                case 11 -> buscarEpisodiosApartirDeUmaData();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
        }
    }

    private void buscarSerie() throws UnsupportedEncodingException {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        serieRepository.save(serie);

        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Informe o nome da série:");
        var nomeSerie = leitura.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");
        var json = consumo.obterDados(URL + nomeSerie + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        
        return dadosSerie;
    }

    private void buscarEpisodioPorSerie() {
        series = serieRepository.findAll();
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if(serie.isPresent()) {
            List<DadosTemporada> temporadas = new ArrayList<>();
            var serieEncontrada = serie.get();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(URL + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                System.out.println("Temps: " + dadosTemporada);
                temporadas.add(dadosTemporada);
            }

            List<Episodio> episodios = temporadas.stream()
                .flatMap(d -> d.episodios().stream()
                    .map(e -> new Episodio(d.numero(), e)))
                .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série informada não existe!");
        }
    }

    private void listarSeriesBuscadas() {
        series = serieRepository.findAll();

        System.out.println("=====================================");
        series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
            .forEach(System.out::println);
        System.out.println("=====================================");
    }

    public List<DadosSerie> getDadosSeries() {
        return dadosSeries;
    }

    public void buscarSeriePorTitulo() {
        System.out.println("Informe o nome da série:");
        var nomeSerie = leitura.nextLine();

        serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Dados da Série: ");
            System.out.println(serieBuscada.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    public void buscarSeriePorAtor() {
        System.out.println("Informe o nome do ator:");
        var nomeAtor = leitura.nextLine();
        System.out.println("Informe a nota da avaliacao:");
        var avaliacao = leitura.nextDouble();

        List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);

        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + ", " + s.getAvaliacao()));
    }

    public void buscarTopSeries() {
        List<Serie> topSeries = serieRepository.findTop5ByOrderByAvaliacaoDesc();

        topSeries.forEach(s -> System.out.println(s.getTitulo() + ", " + s.getAvaliacao()));
    }

    public void buscarSeriesPorCategoria() {
        System.out.println("Informe a categoria:");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero); 
        List<Serie> seriesEncontradas = serieRepository.findByGenero(categoria);

        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + ", " + s.getAvaliacao()));
    }

    public void buscarSeriesPorTemporada() {
        System.out.println("Informe a quantidade de temporadas:");
        var quantidadeTemporadas = leitura.nextInt();
        System.out.println("Informe a quantidade de temporadas:");
        var avaliacao = leitura.nextDouble();

        //List<Serie> seriesEncontradas = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(quantidadeTemporadas, 7.0);
        List<Serie> seriesEncontradas = serieRepository.seriesPorTemporadaEAvaliacao(quantidadeTemporadas, avaliacao);

        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + ", " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Qual o nome do episódio para busca?");
        String nomeBusca = leitura.nextLine();

        List<Episodio> episodios = serieRepository.episodioPorTrecho(nomeBusca);
        episodios.forEach(e ->
            System.out.printf("Série: %s, Temporada: %s, Episódio: %s - $s",
                e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()
            )
        );
    }

    private void topEpisodiosPorSerie() {
        buscarSeriePorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> episodios = serieRepository.topEpisodiosPorSerie(serie);
            episodios.forEach(e -> 
                System.out.printf("Série: %s, Temporadas: $s, Episódio: %s - Avaliação: %s.\n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getAvaliacao())
            );
        }
    }

    private void buscarEpisodiosApartirDeUmaData() {
        buscarSeriePorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            System.out.println("Informe o ano de lançamento:");
            var anoLancamento = leitura.nextInt();
            List<Episodio> episodios = serieRepository.episodiosPorSerieEAno(serie, anoLancamento);
            episodios.forEach(System.out::println);
        }
        
    }
}