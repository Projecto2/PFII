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
@Table(name = "inter_sala_internamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterSalaInternamento.findAll", query = "SELECT i FROM InterSalaInternamento i"),
    @NamedQuery(name = "InterSalaInternamento.findByDescricaoSalaInternamento", query = "SELECT i FROM InterSalaInternamento i WHERE i.descricaoSalaInternamento = :descricaoSalaInternamento"),
    @NamedQuery(name = "InterSalaInternamento.findByCodigoSalaInternamento", query = "SELECT i FROM InterSalaInternamento i WHERE i.codigoSalaInternamento = :codigoSalaInternamento"),
    @NamedQuery(name = "InterSalaInternamento.findByNomeSala", query = "SELECT i FROM InterSalaInternamento i WHERE i.nomeSala = :nomeSala"),
    @NamedQuery(name = "InterSalaInternamento.findByPkIdSalaInternamento", query = "SELECT i FROM InterSalaInternamento i WHERE i.pkIdSalaInternamento = :pkIdSalaInternamento")})
public class InterSalaInternamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 500)
    @Column(name = "descricao_sala_internamento", length = 500)
    private String descricaoSalaInternamento;
    @Size(max = 45)
    @Column(name = "codigo_sala_internamento", length = 45)
    private String codigoSalaInternamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome_sala", nullable = false, length = 45)
    private String nomeSala;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_sala_internamento", nullable = false)
    private Integer pkIdSalaInternamento;
    @JoinColumn(name = "fk_id_enfermaria", referencedColumnName = "pk_id_enfermaria", nullable = false)
    @ManyToOne(optional = false)
    private InterEnfermaria fkIdEnfermaria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdSalaInternamento")
    private List<InterCamaInternamento> interCamaInternamentoList;

    public InterSalaInternamento() {
    }

    public InterSalaInternamento(Integer pkIdSalaInternamento) {
        this.pkIdSalaInternamento = pkIdSalaInternamento;
    }

    public InterSalaInternamento(Integer pkIdSalaInternamento, String nomeSala) {
        this.pkIdSalaInternamento = pkIdSalaInternamento;
        this.nomeSala = nomeSala;
    }

    public String getDescricaoSalaInternamento() {
        return descricaoSalaInternamento;
    }

    public void setDescricaoSalaInternamento(String descricaoSalaInternamento) {
        this.descricaoSalaInternamento = descricaoSalaInternamento;
    }

    public String getCodigoSalaInternamento() {
        return codigoSalaInternamento;
    }

    public void setCodigoSalaInternamento(String codigoSalaInternamento) {
        this.codigoSalaInternamento = codigoSalaInternamento;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public Integer getPkIdSalaInternamento() {
        return pkIdSalaInternamento;
    }

    public void setPkIdSalaInternamento(Integer pkIdSalaInternamento) {
        this.pkIdSalaInternamento = pkIdSalaInternamento;
    }

    public InterEnfermaria getFkIdEnfermaria() {
        return fkIdEnfermaria;
    }

    public void setFkIdEnfermaria(InterEnfermaria fkIdEnfermaria) {
        this.fkIdEnfermaria = fkIdEnfermaria;
    }

    @XmlTransient
    public List<InterCamaInternamento> getInterCamaInternamentoList() {
        return interCamaInternamentoList;
    }

    public void setInterCamaInternamentoList(List<InterCamaInternamento> interCamaInternamentoList) {
        this.interCamaInternamentoList = interCamaInternamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSalaInternamento != null ? pkIdSalaInternamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterSalaInternamento)) {
            return false;
        }
        InterSalaInternamento other = (InterSalaInternamento) object;
        if ((this.pkIdSalaInternamento == null && other.pkIdSalaInternamento != null) || (this.pkIdSalaInternamento != null && !this.pkIdSalaInternamento.equals(other.pkIdSalaInternamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterSalaInternamento[ pkIdSalaInternamento=" + pkIdSalaInternamento + " ]";
    }
    
}
