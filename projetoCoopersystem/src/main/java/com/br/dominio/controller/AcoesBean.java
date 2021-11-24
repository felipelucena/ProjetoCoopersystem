package com.br.dominio.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


import com.br.dominio.model.Acao;
import com.br.dominio.model.Investimento;
import com.br.dominio.util.ApplicationMapUtil;

@Named("acoesBean")
@RequestScoped
public class AcoesBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private Investimento investimento;
	
	private List<Acao> acaoAcimaValorPermitido = new ArrayList<Acao>();
	
	private double saldoTotal;
	private Boolean validaValResgatarVazios;
	
	@PostConstruct
	void inti() {
		investimento = (Investimento) ApplicationMapUtil.getValueFromApplicationMap("investimento");
		carregarListaResgate();
		saldoTotal = investimento.getSaldoTotal();
	}

	private BigDecimal arredondar(Double valor) {
		BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);
		return bd;
	}

	public List<Acao> carregarListaResgate() {
		Double saldoAcumulado;
		validaValResgatarVazios = Boolean.TRUE;
		for (Acao acao : investimento.getAcoes()) {
			if (acao != null && acao.getId() != 0L) {
				if (acao.getPercentual() != null) {
					saldoAcumulado = (acao.getPercentual() / 100) * investimento.getSaldoTotal();
					acao.setSaldoAcumulado(arredondar(saldoAcumulado).doubleValue());
					if (acao.getValorResgatado() != 0.0) {
						this.validaValResgatarVazios = Boolean.FALSE;
					}
				}
			}
		}
		return investimento.getAcoes();
	}

	public void calculaSaldoTotal() {
		Double valorTotalResgatado = 0.0;
		for (Acao acao : investimento.getAcoes()) {
			valorTotalResgatado += acao.getSaldoAcumulado() - acao.getValorResgatado();
		}
		this.saldoTotal = valorTotalResgatado;
		System.out.println(valorTotalResgatado);
		acaoAcimaValorPermitido = validaConcluir();
	}

	public List<Acao> validaConcluir() {
		acaoAcimaValorPermitido = new ArrayList<Acao>();
		for (Acao acao : investimento.getAcoes()) {
			if (acao.getValorResgatado() != null) {
				if (acao.getValorResgatado() >= acao.getSaldoAcumulado()) {
					acaoAcimaValorPermitido.add(acao);
				}
			}
		}
		return acaoAcimaValorPermitido.stream().distinct().collect(Collectors.toList());
	}

	public Boolean validaCampoValorResgatado(Double valorResgatado, Double saldoAcumulado) {
		if (saldoAcumulado != null || valorResgatado != null) {
			return saldoAcumulado <= valorResgatado;
		} else {
			return true;
		}
	}

	public Investimento getInvestimento() {
		return investimento;
	}

	public void setInvestimento(Investimento investimento) {
		this.investimento = investimento;
	}

	public double getSaldoTotal() {
		return saldoTotal;
	}

	public void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	public List<Acao> getAcaoAcimaValorPermitido() {
		return acaoAcimaValorPermitido;
	}

	public void setAcaoAcimaValorPermitido(List<Acao> acaoAcimaValorPermitido) {
		this.acaoAcimaValorPermitido = acaoAcimaValorPermitido;
	}

	public Boolean getValidaValResgatarVazios() {
		return validaValResgatarVazios;
	}

	public void setValidaValResgatarVazios(Boolean validaValResgatarVazios) {
		this.validaValResgatarVazios = validaValResgatarVazios;
	}

}
