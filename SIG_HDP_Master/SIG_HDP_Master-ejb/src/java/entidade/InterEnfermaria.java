/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_enfermaria", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterEnfermaria.findAll", query = "SELECT i FROM InterEnfermaria i"),
    @NamedQuery(name = "InterEnfermaria.findByDescricao", query = "SELECT i FROM InterEnfermaria i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterEnfermaria.findByCodigoEnfermaria", query = "SELECT i FROM InterEnfermaria i WHERE i.codigoEnfermaria = :codigoEnfermaria"),
    @NamedQuery(name = "InterEnfermaria.findByPkIdEnfermaria", query = "SELECT i FROM InterEnfermaria i WHERE i.pkIdEnfermaria = :pkIdEnfermaria")})
public class InterEnfermaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @Size(max = 45)
    @Column(name = "codigo_enfermaria", length = 45)
    private String codigoEnfermaria;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_enfermaria", nullable = false)
    private Integer pkIdEnfermaria;
    @OneToMany(mappedBy = "fkIdEnfermaria")
    private List<SupiLocalConsulta> supiLocalConsultaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEnfermaria")
    private List<InterSalaInternamento> interSalaInternamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEnfermaria")
    private List<InterNotificacao> interNotificacaoList;
    @JoinColumn(name = "fk_id_servico", referencedColumnName = "pk_id_servico", nullable = false)
    @ManyToOne(optional = false)
    private AdmsServico fkIdServico;
    @OneToMany(mappedBy = "fkIdEnfermaria")
    private List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList;

    public InterEnfermaria() {
    }

    public InterEnfermaria(Integer pkIdEnfermaria) {
        this.pkIdEnfermaria = pkIdEnfermaria;
    }

    public InterEnfermaria(Integer pkIdEnfermaria, String descricao) {
        this.pkIdEnfermaria = pkIdEnfermaria;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoEnfermaria() {
        return codigoEnfermaria;
    }

    public void setCodigoEnfermaria(String codigoEnfermaria) {
        this.codigoEnfermaria = codigoEnfermaria;
    }

    public Integer getPkIdEnfermaria() {
        return pkIdEnfermaria;
    }

    public void setPkIdEnfermaria(Integer pkIdEnfermaria) {
        this.pkIdEnfermaria = pkIdEnfermaria;
    }

    @XmlTransient
    public List<SupiLocalConsulta> getSupiLocalConsultaList() {
        return supiLocalConsultaList;
    }

    public void setSupiLocalConsultaList(List<SupiLocalConsulta> supiLocalConsultaList) {
        this.supiLocalConsultaList = supiLocalConsultaList;
    }

    @XmlTransient
    public List<InterSalaInternamento> getInterSalaInternamentoList() {
        return interSalaInternamentoList;
    }

    public void setInterSalaInternamentoList(List<InterSalaInternamento> interSalaInternamentoList) {
        this.interSalaInternamentoList = interSalaInternamentoList;
    }

    @XmlTransient
    public List<InterNotificacao> getInterNotificacaoList() {
        return interNotificacaoList;
    }

    public void setInterNotificacaoList(List<InterNotificacao> interNotificacaoList) {
        this.interNotificacaoList = interNotificacaoList;
    }

    public AdmsServico getFkIdServico() {
        return fkIdServico;
    }

    public void setFkIdServico(AdmsServico fkIdServico) {
        this.fkIdServico = fkIdServico;
    }

    @XmlTransient
    public List<SupiSupervisorHasEscala> getSupiSupervisorHasEscalaList() {
        return supiSupervisorHasEscalaList;
    }

    public void setSupiSupervisorHasEscalaList(List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList) {
        this.supiSupervisorHasEscalaList = supiSupervisorHasEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEnfermaria != null ? pkIdEnfermaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterEnfermaria)) {
            return false;
        }
        InterEnfermaria other = (InterEnfermaria) object;
        if ((this.pkIdEnfermaria == null && other.pkIdEnfermaria != null) || (this.pkIdEnfermaria != null && !this.pkIdEnfermaria.equals(other.pkIdEnfermaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterEnfermaria[ pkIdEnfermaria=" + pkIdEnfermaria + " ]";
    }
    
}
