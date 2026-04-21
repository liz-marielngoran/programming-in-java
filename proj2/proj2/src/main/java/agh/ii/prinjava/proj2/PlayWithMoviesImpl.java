import agh.ii.prinjava.proj2.PlayWithMovies;
import agh.ii.prinjava.proj2.dal.ImdbTop250;
import agh.ii.prinjava.proj2.model.Movie;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayWithMoviesImpl implements PlayWithMovies {

    private static Stream<Movie> movies() {
        return ImdbTop250.movies().orElse(List.of()).stream();
    }

    /**
     * Returns the movies (only titles) directed (or co-directed) by a given director
     */
    static Set<String> ex01(String director) {
        return movies()
                .filter(m -> m.directors().contains(director))
                .map(Movie::title)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the movies (only titles) in which an actor played
     */
    static Set<String> ex02(String actor) {
        return movies()
                .filter(m -> m.actors().contains(actor))
                .map(Movie::title)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the number of movies per director (as a map)
     */
    static Map<String, Long> ex03() {
        return movies()
                .flatMap(m -> m.directors().stream())
                .collect(Collectors.groupingBy(
                        director -> director,
                        Collectors.counting()
                ));
    }

    /**
     * Returns the 10 directors with the most films on the list
     */
    static Map<String, Long> ex04() {
        return ex03().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Returns the movies (only titles) made by each of the 10 directors found in ex04
     */
    static Map<String, Set<String>> ex05() {
        Set<String> top10Directors = ex04().keySet();
        return movies()
                .flatMap(m -> m.directors().stream()
                        .filter(top10Directors::contains)
                        .map(director -> Map.entry(director, m.title()))
                )
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }

    /**
     * Returns the number of movies per actor (as a map)
     */
    static Map<String, Long> ex06() {
        return movies()
                .flatMap(m -> m.actors().stream())
                .collect(Collectors.groupingBy(
                        actor -> actor,
                        Collectors.counting()
                ));
    }

    /**
     * Returns the 9 actors with the most films on the list
     */
    static Map<String, Long> ex07() {
        return ex06().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(9)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Returns the movies (only titles) of each of the 9 actors from ex07
     */
    static Map<String, Set<String>> ex08() {
        Set<String> top9Actors = ex07().keySet();
        return movies()
                .flatMap(m -> m.actors().stream()
                        .filter(top9Actors::contains)
                        .map(actor -> Map.entry(actor, m.title()))
                )
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }

    /**
     * Returns the 5 most frequent actor partnerships (i.e., appearing together most often).
     * A partnership key is formatted as "Actor A, Actor B" (alphabetical order).
     */
    static Map<String, Long> ex09() {
        return movies()
                .flatMap(m -> {
                    List<String> actors = new ArrayList<>(m.actors());
                    List<String> pairs = new ArrayList<>();
                    for (int i = 0; i < actors.size(); i++) {
                        for (int j = i + 1; j < actors.size(); j++) {
                            // sort alphabetically to ensure consistent key
                            String a = actors.get(i);
                            String b = actors.get(j);
                            String pair = a.compareTo(b) <= 0
                                    ? a + ", " + b
                                    : b + ", " + a;
                            pairs.add(pair);
                        }
                    }
                    return pairs.stream();
                })
                .collect(Collectors.groupingBy(pair -> pair, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Returns the movies (only titles) of each of the 5 most frequent actor partnerships
     */
    static Map<String, Set<String>> ex10() {
        Set<String> top5Pairs = ex09().keySet();
        return movies()
                .flatMap(m -> {
                    List<String> actors = new ArrayList<>(m.actors());
                    List<Map.Entry<String, String>> pairs = new ArrayList<>();
                    for (int i = 0; i < actors.size(); i++) {
                        for (int j = i + 1; j < actors.size(); j++) {
                            String a = actors.get(i);
                            String b = actors.get(j);
                            String pair = a.compareTo(b) <= 0
                                    ? a + ", " + b
                                    : b + ", " + a;
                            if (top5Pairs.contains(pair)) {
                                pairs.add(Map.entry(pair, m.title()));
                            }
                        }
                    }
                    return pairs.stream();
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }
}
