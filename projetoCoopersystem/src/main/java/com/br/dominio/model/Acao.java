package com.br.dominio.model;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

public class Acao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	private Double percentual;
	
	private Double saldoAcumulado;
	
	private Double valorResgatado;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPercentual() {
		return percentual;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}
	
	
	public Double getSaldoAcumulado() {
		return saldoAcumulado;
	}

	public void setSaldoAcumulado(Double saldoAcumulado) {
		this.saldoAcumulado = saldoAcumulado;
	}

	public Double getValorResgatado() {
		return valorResgatado != null ? valorResgatado : 0.0;
	}

	public void setValorResgatado(Double valorResgatado) {
		this.valorResgatado = valorResgatado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acao other = (Acao) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
