package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;


@SuppressWarnings("serial")
@Entity
@Table(name = "politica_colocacao_tipovendedor_tipo_produto")
public class PoliticaColocacaoTipoVendedorTipoProduto extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private TipoVendedor tipoVendedor;
	private TipoProduto tipoProduto;
	private List<IntervaloColocacaoTipoProduto> listaIntervalos = new ArrayList<>();
	
	
	@ManyToOne
	@JoinColumn(name = "tipo_vendedor_id", nullable = false)
	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	@ManyToOne
	@JoinColumn(name = "tipo_produto_id", nullable = false)
	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	@OneToMany(mappedBy = "politicaColocacaoTipoVendedorTipoProduto", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<IntervaloColocacaoTipoProduto> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<IntervaloColocacaoTipoProduto> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}
	
	@Transient
	public boolean adicionaIntervaloColocacao(IntervaloColocacaoTipoProduto intervalo) {
		//Verifica se o novo intervalo esta dentro de um intervalo maior.
		if (listaIntervalos.size() != 0) {
			for (IntervaloColocacaoTipoProduto i : listaIntervalos) {
				if (i.estaNoIntervalo(intervalo.getInicio())  || i.estaNoIntervalo(intervalo.getFim())) {
					return false;
				}
			}
		}
		//Verifica se o novo intervalo engloba os demais
		if(listaIntervalos.size() != 0) {
			for (IntervaloColocacaoTipoProduto i : listaIntervalos) {
				if(i.getInicio() >= intervalo.getInicio() && i.getFim() <= intervalo.getFim()) {
					return false;
				}
			}
		}
		
		listaIntervalos.add(intervalo);
		return true;
	}
	
	//Retorna a comissão colocacao dado um intervalo
		public BigDecimal comissao(int quantidade) {
			for(IntervaloColocacaoTipoProduto i : listaIntervalos) {
				if(i.estaNoIntervalo(quantidade)) {
					return i.getValor();
				}
			}
			return BigDecimal.ZERO;
			
		}
		
		
		//Retorna a premiacao dado um intervalo
				public BigDecimal premiacao(int quantidade) {
					for(IntervaloColocacaoTipoProduto i : listaIntervalos) {
						if(i.estaNoIntervalo(quantidade)) {
							return i.getPremiacao();
						}
					}
					return BigDecimal.ZERO;
					
				}
	
	
}
