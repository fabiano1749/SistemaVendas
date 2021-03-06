package com.cepalab.sistemaVendas.cadastro.dominio;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="produto")
public class Produto extends GenericDTO{

	private static final long serialVersionUID = 1L;
		
	private String nome;
	private BigDecimal custo = BigDecimal.ZERO;
	private int posicao;
	private PodeConsignar podeConsignar;
	private TipoProduto tipo = new TipoProduto();
	
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@Column(nullable=false, length=40)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	

	@Column(nullable=false, precision=10, scale=2)
	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	
	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name= "pode_consignar", length=5, nullable=false)
	public PodeConsignar getPodeConsignar() {
		return podeConsignar;
	}

	public void setPodeConsignar(PodeConsignar podeConsignar) {
		this.podeConsignar = podeConsignar;
	}

	@ManyToOne
	@JoinColumn(nullable = false, name = "tipo_id")
	public TipoProduto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}

}
