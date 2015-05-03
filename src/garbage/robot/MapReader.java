package garbage.robot;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Rafa³ Swedra Klasa obs³uguj¹ca wczytanie z pliku zarysu mapy.
 *
 */
public class MapReader {
	private char[][] mapTab;
	private int mapX;
	private int mapY;

	/**
	 * konstruktor
	 * 
	 * @param mapX
	 *            Szerokosc mapy
	 * @param mapY
	 *            Wysokosc mapy
	 */
	public MapReader(int mapX, int mapY) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.mapTab = new char[mapY][mapX];
	}

	/**
	 * Metoda wczytuje mape z pliku do tablicy mapTab
	 * 
	 * @param fileLocation
	 *            Lokalizacja pliku z mapa
	 */
	public void loadMapFromFile(String fileLocation) {
		FileReader fr = null;
		String linia = "";

		// OTWIERANIE PLIKU:
		try {
			fr = new FileReader(fileLocation);
		} catch (FileNotFoundException e) {
			System.err.println("B£¥D PRZY OTWIERANIU PLIKU!");
			System.exit(1);
		}

		BufferedReader bfr = new BufferedReader(fr);
		// ODCZYT KOLEJNYCH LINII Z PLIKU:
		int currentY = 0;
		try {
			while ((linia = bfr.readLine()) != null) {
				for (int i = 0; i < mapX; i++) {
					mapTab[currentY][i] = linia.charAt(i);
				}
				currentY += 1;

			}
		} catch (IOException e) {
			System.err.println("B£¥D ODCZYTU Z PLIKU!");
			System.exit(2);
		}

		// ZAMYKANIE PLIKU
		try {
			fr.close();
		} catch (IOException e) {
			System.err.println("B£¥D PRZY ZAMYKANIU PLIKU!");
			System.exit(3);
		}
	}

	/**
	 *  Metoda wyswietla wczytana mape.
	 */
	public void displayMap() {
		for (int i = 0; i < mapY; i++) {
			for (int j = 0; j < mapX; j++) {
				System.out.print(mapTab[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//gettery i settery

	public char[][] getMapTab() {
		return mapTab;
	}

	public void setMapTab(char[][] mapTab) {
		this.mapTab = mapTab;
	}

	public int getMapX() {
		return mapX;
	}

	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

}
