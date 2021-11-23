package com.br.dominio.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.br.dominio.model.Acao;
import com.br.dominio.model.Investimento;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class LeitorJsonUtil {

	public List<Investimento> lerJsaon() throws JsonSyntaxException, IOException, JSONException {
		List<Investimento> investimento = new ArrayList<Investimento>();
		JsonParser parser = new JsonParser();

		try {
			
			JSONObject json = readJsonFromUrl("https://run.mocky.io/v3/7b2dfe42-37a3-4094-b7ce-8ee4f8012f30");
			JSONObject te = (JSONObject) json.get("response");
			 JSONObject te2 = (JSONObject) te.get("data");
			JSONArray titulosNaoConciliados = te2.getJSONArray("listaInvestimentos");
			titulosNaoConciliados.forEach(x -> investimento.add(retornaObjetoInvestimento((JSONObject) x)));
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return investimento;
	}
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	 
	 public Investimento retornaObjetoInvestimento(JSONObject jsonObject) {
		 Investimento investimento = new Investimento();
		 investimento.setAcoes(new ArrayList<Acao>());
		 	investimento.setNome(jsonObject.get("nome").toString());
			investimento.setObjetivo( (String) jsonObject.get("objetivo").toString());
			investimento.setSaldoTotal( (Double) Double.parseDouble(jsonObject.get("saldoTotal").toString()));
			investimento.setIndicadorDeCarencia((String) jsonObject.get("indicadorCarencia").toString());
			JSONArray listaJsonAcao = jsonObject.getJSONArray("acoes");
			listaJsonAcao.forEach(x -> investimento.getAcoes().add(retornaObjetoAcao((JSONObject) x)));
			return investimento;
	 }
	 
	 public Acao retornaObjetoAcao(JSONObject jsonObject) {
		 Acao acao = new Acao();
		 acao.setId( (Long) Long.parseLong(jsonObject.get("id").toString()));
		 acao.setNome( (String) jsonObject.get("nome").toString());
		 acao.setPercentual( (Double) Double.parseDouble(jsonObject.get("percentual").toString()));
		 return acao;
	 }
	 
}
