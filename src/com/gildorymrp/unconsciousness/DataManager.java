package com.gildorymrp.unconsciousness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataManager {

	public static void loadLocations(GildorymUnconsciousness plugin) {
		try {
			File file = new File(plugin.getDataFolder().getAbsolutePath()
					+ File.separator + "locations.dat");
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					String[] data = line.split(" ");
					if (data.length == 4) {
						Integer[] coords = new Integer[3];
						try {
							coords[0] = Integer.parseInt(data[1]);
							coords[1] = Integer.parseInt(data[2]);
							coords[2] = Integer.parseInt(data[3]);
						} catch (NumberFormatException ex) {

						} catch (NullPointerException ex) {

						}
						plugin.locationMap.put(data[0], coords);
					}
				}
				br.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadMessages(GildorymUnconsciousness plugin) {
		try {
			File file = new File(plugin.getDataFolder().getAbsolutePath()
					+ File.separator + "messages.dat");
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					String[] data = line.split(" ");
					if (data.length > 1) {
						plugin.messageMap.put(data[0], 
								line.substring(line.indexOf(' ') + 1));
					}
				}
				br.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}