import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataProcessingUnit {

	/**
	 * 
	 * @param height
	 * @return
	 */
	public static int stringToInches(String height) {
		String[] heightArr = height.replaceAll("\"", " ").trim().split("'");
		try {
			return 12 * Integer.valueOf(heightArr[0]) + Integer.valueOf(heightArr[1]);
		} 
		catch (Exception e) {
			return 0;
		}  
	}

	/**
	 * 
	 * @param inches
	 * @return
	 */
	public static String inchesToString(double inches) {
		int feet = (int) inches / 12;
		int newInches = (int) inches % 12;
		return "" + feet + "'" + newInches;
	}

	/**
	 * 
	 * @param x
	 * @param comparison
	 * @param y
	 * @return
	 */
	public static boolean makeComparison(String[] x, String comparison, String[] y) {
		double xPercent = (double) Integer.valueOf(x[4]) / Integer.valueOf(x[3]);
		double yPercent = (double) Integer.valueOf(y[4]) / Integer.valueOf(y[3]);
		if (comparison.equals(">")) {
			return xPercent > yPercent;
		} else {
			return xPercent < yPercent;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void flyingEtiquetteQuestion1() throws Exception {
		Stream<String> stream = Files.lines(Paths.get("data/flying-etiquette.csv"));	
		long yesHeightSum = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[12].contains("Yes"))
				.map(s -> stringToInches(s[3]))
				.reduce(0, (x,y) -> x+y);
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long yesNumOfPeople = stream
				.filter(s -> (!s.contains("||||||||")))
				.filter(s -> (s.contains("'")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[12].contains("Yes"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));	
		long noHeightSum = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[12].contains("No"))
				.map(s -> stringToInches(s[3]))
				.reduce(0, (x,y) -> x+y);
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long noNumOfPeople = stream
				.filter(s -> (!s.contains("||||||||")))
				.filter(s -> (s.contains("'")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[12].contains("No"))
				.count();

		double yesAvgHeight = (double) yesHeightSum / yesNumOfPeople;
		double noAvgHeight = (double) noHeightSum / noNumOfPeople;
		System.out.println("On average, how tall are people who think it is rude/not rude to recline one's seat?");
		System.out.println("> " + "Average for rude: " + inchesToString(yesAvgHeight) + "\"");
		System.out.println("> " + "Average for not rude: " + inchesToString(noAvgHeight) + "\"" + "\n");
		stream.close();
	}

	/**
	 * @throws IOException 
	 * 
	 */
	@SuppressWarnings("resource")
	public static void flyingEtiquetteQuestion2() throws Exception {
		Stream<String> stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long haveChildrenRude = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[4].contains("Yes"))
				.filter(s -> s[19].contains("Yes"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long haveChildrenTotal = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[4].contains("Yes"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long noChildrenRude = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[4].contains("No"))
				.filter(s -> s[19].contains("Yes"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long noChildrenTotal = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[4].contains("No"))
				.count();

		System.out.println("How much more likely are people without children under the age of 18 to think that it is rude" + "\n" + "to bring unruly children on a plane than people with children under the age of 18?");
		System.out.println("> " + ((double) noChildrenRude/noChildrenTotal) / ((double) haveChildrenRude/haveChildrenTotal) + "\n");
		stream.close();
	}

	@SuppressWarnings("resource")
	public static void flyingEtiquetteQuestion3() throws Exception {
		Stream<String> stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long antiSocial = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.filter(s -> s[9].contains("Yes"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/flying-etiquette.csv"));
		long flyersTotal = stream
				.filter(s -> (!s.contains("||||||||")))
				.map(s -> s.split("\\|"))
				.map(s -> s[9])
				.count();

		double anti = (double) antiSocial;
		double total = (double) flyersTotal;
		System.out.println("What percentage of flyers believe it is rude to say more than a few words on a flight?");
		System.out.println("> " + anti/total*100 + "%" + "\n");
		stream.close();
	}

	/**
	 * @throws IOException 
	 * 
	 */
	@SuppressWarnings("resource")
	public static void starWarsQuestion1() throws Exception {
		Stream<String> stream = Files.lines(Paths.get("data/StarWars.csv"));
		long numLikesJarJar = stream
				.filter(s -> !s.contains("||||||||||"))
				.map(s -> s.split("\\|"))
				.map(s -> s[26])
				.filter(s -> s.contains("Very favorably"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/StarWars.csv"));
		long totalPeopleJarJar = stream
				.filter(s -> !s.contains("||||||||||"))
				.map(s -> s.split("\\|"))
				.count();

		double ratioPeople = (double) numLikesJarJar / totalPeopleJarJar;
		System.out.println("What percent of people actually like Jar Jar Binks?");
		System.out.println("> " + ratioPeople*100 + "%" + "\n");
		stream.close();
	}

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void starWarsQuestion2() throws Exception {
		Stream<String> stream = Files.lines(Paths.get("data/StarWars.csv"));
		long totalCloneAttack = stream
				.filter(s -> !s.contains("||||||||||"))
				.filter(s -> !s.contains("Attack of the Clones"))
				.map(s -> s.split("\\|"))
				.map(s -> s[10])
				.map(s -> Integer.valueOf(s))
				.reduce(0, (x,y) -> x+y);

		stream.close();
		stream = Files.lines(Paths.get("data/StarWars.csv"));
		long totalPeopleCloneRating = stream
				.filter(s -> !s.contains("||||||||||"))
				.filter(s -> !s.contains("Attack of the Clones"))
				.map(s -> s.split("\\|"))
				.count();

		double cloneAvg = (double) totalCloneAttack / totalPeopleCloneRating;
		System.out.println("What is the average rating of Clone Wars with 1 as favorite, 6 as least favorite?");
		System.out.println("> " + cloneAvg + "\n");
		stream.close();
	}

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void starWarsQuestion3() throws Exception {
		Stream<String> stream = Files.lines(Paths.get("data/StarWars.csv"));
		long totalNerds = stream
				.filter(s -> !s.contains("|||||||||"))
				.map(s -> s.split("\\|"))
				.filter(s -> s[31].contains("Yes"))
				.count();
		stream.close();

		stream = Files.lines(Paths.get("data/StarWars.csv"));
		long totalMaleNerds = stream
				.filter(s -> !s.contains("|||||||||"))
				.filter(s -> s.contains("Male"))
				.map(s -> s.split("\\|"))
				.filter(s -> s[31].contains("Yes"))
				.count();

		double maleNerdPct = (double) totalMaleNerds*100 / totalNerds;
		System.out.println("What percent of people who are fans of the Star Wars Extended Universe are male?");
		System.out.println("> " + maleNerdPct + "%" + "\n");
		stream.close();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public static void collegeMajorsQuestion1() throws Exception {
		String[] arrInitial = {"1", "1", "1", "2", "1", "1", "2", "1", "1"};
		Stream<String> stream = Files.lines(Paths.get("data/college-majors.csv"));
		String[] mostEmployed = stream
				.filter(s -> !s.contains("Major_code"))
				.map(s -> s.split("\\|"))
				.reduce(arrInitial, (x,y) -> (makeComparison(x, ">", y) ? x : y));

		System.out.println("Which major has the highest percentage of employed graduates?");
		System.out.println("> " + mostEmployed[1].toLowerCase() + "\n");
		stream.close();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public static void collegeMajorsQuestion2() throws Exception {
		String[] arrInitial = {"1", "1", "1", "2", "1", "1", "2", "1", "1"};
		Stream<String> stream = Files.lines(Paths.get("data/college-majors.csv"));
		String[] leastEmployed = stream
				.filter(s -> !s.contains("Major_code"))
				.map(s -> s.split("\\|"))
				.reduce(arrInitial, (x,y) -> (makeComparison(x, "<", y) ? x : y));

		System.out.println("Which major has the lowest percentage of employed graduates?");
		System.out.println("> " + leastEmployed[1].toLowerCase() + "\n");
		stream.close();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public static void collegeMajorsQuestion3() throws Exception {
		String[] arrInitial = {"1", "1", "1", "2", "1", "1", "2", "1", "1"};
		Stream<String> stream = Files.lines(Paths.get("data/college-majors.csv"));
		String[] highestEarning = stream
				.filter(s -> !s.contains("Major_code"))
				.map(s -> s.split("\\|"))
				.reduce(arrInitial, (x,y) -> (Integer.valueOf(x[8]) > Integer.valueOf(y[8]) ? x : y));

		System.out.println("Which major has the highest median salary after graduation?");
		System.out.println("> " + highestEarning[1].toLowerCase());		
		stream.close();
	}
	
	public static void main(String args[]) throws Exception {
		flyingEtiquetteQuestion1();
		flyingEtiquetteQuestion2();
		flyingEtiquetteQuestion3();
		starWarsQuestion1();
		starWarsQuestion2();
		starWarsQuestion3();
		collegeMajorsQuestion1();
		collegeMajorsQuestion2();
		collegeMajorsQuestion3();
	}
}