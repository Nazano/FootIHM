package app;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import gestion.CsvUtils;
import gestion.DataManager;

public class Simulation {
	public static void main(String[] args) {
		System.out.println("Bonjour");
		DataManager dm = new DataManager("2013-11-03_tromso_stromsgodset_first.csv");
		dm.findEnregistrements();
		System.out.println("-----------------------");
		System.out.println(dm.getPos(2,5));
		System.out.println("fin chargement donn�es");
		dm.loadStats();
		System.out.println( dm.getPresenceMax(14));
		}
}