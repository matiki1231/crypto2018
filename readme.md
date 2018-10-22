1. Co wypisze poniższy fragment programu?

   ```java
   System.out.println(Math.min(Double.MIN_VALUE, 0.0d));
   ```

   Odpowiedź: `0.0`

2. Jaki będzie wynik poniższego wyrażenia?

   ```java
   3 + 2 + "C" + 2 * 2 + "L" + 11 / 4 * 2
   ```

   Odpowiedź: `"5C4L4"`

3. Spraw aby program nie wydrukował komunikatu `"Gołąb!"` edytując jedynie zawartość bloku `try` (program musi się kompilować).

   ```java
   try {
   
   } finally {
     System.out.println("Gołąb!");
   }
   ```

   Odpowiedź (jedna z): `System.exit(0);`

4. Zaproponuj implementację dla poniższej funkcji. Ma ona na podstawie podanej funkcji utworzyć nową funkcję, która wynik dla konkretnej wartości argumentu liczy tylko raz (cache'uje wyniki).

   ```java
   public static <A, R> Function<A, R> memoize(Function<A, R> function)
   ```

   Odpowiedź:

   ```java
   Map<A, R> cache = new ConcurrentHashMap<>();
   return a -> cache.computeIfAbsent(a, function);
   ```

5. Zaproponuj implementację dla poniższej funkcji. Ma ona liczyć wystąpienia słów we fragmencię tekstu (książki).
Wynikiem funkcji ma być posortowana lista par `słowo=liczba_wystąpień` względem liczby wystąpień (malejąco) i leksykograficznie względem słowa.

   Do reprezentacji pary (słowo, liczba) wykorzystaj klasę `WordCount`. Posiada ona konstruktor `WordCount(String word, long count)` oraz akcesory `getWord` oraz `getCount`.

   ```java
   public static List<WordCount> calculateSortedWordCount(String text)
   ```

   Odpowiedź:

   ```java
   return Arrays.stream(text.split("[\\s.\",:;]+"))
     .map(String::toLowerCase)
     .collect(groupingBy(identity(), counting()))
     .entrySet()
     .stream()
     .sorted(comparing(Map.EntryString, Long::getValue)
       .reversed()
       .thenComparing(Map.Entry::getKey))
     .collect(toList());
   ```
