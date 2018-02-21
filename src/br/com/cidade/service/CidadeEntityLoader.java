package br.com.cidade.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.cidade.model.entity.Cidade;

/**
 * 
 * @author Jose Wollinger
 * Feb 20, 2018
 */
public class CidadeEntityLoader {
	
	private static final String CSV_FILE_PATH = "doc\\cidades.csv";
	private static final String CSV = ",";
	
	public static List<Cidade> loadCidadesFromCsvFile(){
		List<Cidade> listCidade = new ArrayList();
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
			br.readLine(); //Headers
			while((line = br.readLine()) != null) {
				String[] cidade = line.split(CSV);
				adicionarCidade(listCidade, cidade);
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		return listCidade;
	}

	private static void adicionarCidade(List<Cidade> listCidade, String[] cidade) {
		listCidade.add(new Cidade(new Long(cidade[0]),cidade[1], cidade[2], new Boolean(cidade[3]),
				new Double(cidade[4]), new Double(cidade[5]),cidade[6],cidade[7],cidade[8], cidade[9]));
	}

}
