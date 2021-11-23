package com.br.dominio.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.jettison.json.JSONException;
import javax.faces.bean.ViewScoped;

import com.br.dominio.model.Investimento;
import com.br.dominio.util.ApplicationMapUtil;
import com.br.dominio.util.LeitorJsonUtil;
import com.google.gson.JsonSyntaxException;

@Named("beanInvest")
@ViewScoped
public class InvestimentoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Investimento investimento;
	
	@Inject
	private LeitorJsonUtil leitorJsonUtil;
	
	private List<Investimento> investimentos = new ArrayList<Investimento>();

	@PostConstruct
	public void iniciarLista() throws JsonSyntaxException, IOException, JSONException {
		investimentos = leitorJsonUtil.lerJsaon();
		
	}
	
	public String selecionar(Investimento investimento) {
		ApplicationMapUtil.setValueInApplicationMap("investimento", investimento);
		return "/Resgate.xhtml?faces-redirect=true";
	}
	

	public Investimento getInvestimento() {
		return investimento;
	}

	public void setInvestimento(Investimento investimento) {
		this.investimento = investimento;
	}

	public List<Investimento> getInvestimentos() {
		return investimentos;
	}

	public void setInvestimentos(List<Investimento> investimentos) {
		this.investimentos = investimentos;
	}

}
