/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_prescricao_medica_has_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbPrescricaoMedicaHasProduto.findAll", query = "SELECT a FROM AmbPrescricaoMedicaHasProduto a"),
    @NamedQuery(name = "AmbPrescricaoMedicaHasProduto.findByPkIdPrescricaoMedicaProduto", query = "SELECT a FROM AmbPrescricaoMedicaHasProduto a WHERE a.pkIdPrescricaoMedicaProduto = :pkIdPrescricaoMedicaProduto"),
    @NamedQuery(name = "AmbPrescricaoMedicaHasProduto.findByQuantidade", query = "SELECT a FROM AmbPrescricaoMedicaHasProduto a WHERE a.quantidade = :quantidade"),
    @NamedQuery(name = "AmbPrescricaoMedicaHasProduto.findByObservacoes", query = "SELECT a FROM AmbPrescricaoMedicaHasProduto a WHERE a.observacoes = :observacoes")})
public class AmbPrescricaoMedicaHasProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_prescricao_medica_produto", nullable = false)
    private Long pkIdPrescricaoMedicaProduto;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Size(max = 255)
    @Column(name = "observacoes", length = 255)
    private String observacoes;
    @JoinColumn(name = "fk_id_estado_pagamento", referencedColumnName = "pk_id_estado_pagamento")
    @ManyToOne
    private AmbEstadoPagamento fkIdEstadoPagamento;
    @JoinColumn(name = "fk_id_prescricao_medica", referencedColumnName = "pk_id_prescricao_medica", nullable = false)
    @ManyToOne(optional = false)
    private AmbPrescricaoMedica fkIdPrescricaoMedica;
    @JoinColumn(name = "fk_id_farm_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdFarmProduto;
    @JoinColumn(name = "fk_id_hora_medicacao", referencedColumnName = "pk_id_hora_medicacao", nullable = false)
    @ManyToOne(optional = false)
    private InterHoraMedicacao fkIdHoraMedicacao;

    public AmbPrescricaoMedicaHasProduto() {
    }

    public AmbPrescricaoMedicaHasProduto(Long pkIdPrescricaoMedicaProduto) {
        this.pkIdPrescricaoMedicaProduto = pkIdPrescricaoMedicaProduto;
    }

    public Long getPkIdPrescricaoMedicaProduto() {
        return pkIdPrescricaoMedicaProduto;
    }

    public void setPkIdPrescricaoMedicaProduto(Long pkIdPrescricaoMedicaProduto) {
        this.pkIdPrescricaoMedicaProduto = pkIdPrescricaoMedicaProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public AmbEstadoPagamento getFkIdEstadoPagamento() {
        return fkIdEstadoPagamento;
    }

    public void setFkIdEstadoPagamento(AmbEstadoPagamento fkIdEstadoPagamento) {
        this.fkIdEstadoPagamento = fkIdEstadoPagamento;
    }

    public AmbPrescricaoMedica getFkIdPrescricaoMedica() {
        return fkIdPrescricaoMedica;
    }

    public void setFkIdPrescricaoMedica(AmbPrescricaoMedica fkIdPrescricaoMedica) {
        this.fkIdPrescricaoMedica = fkIdPrescricaoMedica;
    }

    public FarmProduto getFkIdFarmProduto() {
        return fkIdFarmProduto;
    }

    public void setFkIdFarmProduto(FarmProduto fkIdFarmProduto) {
        this.fkIdFarmProduto = fkIdFarmProduto;
    }

    public InterHoraMedicacao getFkIdHoraMedicacao() {
        return fkIdHoraMedicacao;
    }

    public void setFkIdHoraMedicacao(InterHoraMedicacao fkIdHoraMedicacao) {
        this.fkIdHoraMedicacao = fkIdHoraMedicacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPrescricaoMedicaProduto != null ? pkIdPrescricaoMedicaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbPrescricaoMedicaHasProduto)) {
            return false;
        }
        AmbPrescricaoMedicaHasProduto other = (AmbPrescricaoMedicaHasProduto) object;
        if ((this.pkIdPrescricaoMedicaProduto == null && other.pkIdPrescricaoMedicaProduto != null) || (this.pkIdPrescricaoMedicaProduto != null && !this.pkIdPrescricaoMedicaProduto.equals(other.pkIdPrescricaoMedicaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbPrescricaoMedicaHasProduto[ pkIdPrescricaoMedicaProduto=" + pkIdPrescricaoMedicaProduto + " ]";
    }
    
}
