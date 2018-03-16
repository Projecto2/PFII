/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_tratamento_malnutricao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterTratamentoMalnutricao.findAll", query = "SELECT i FROM InterTratamentoMalnutricao i"),
    @NamedQuery(name = "InterTratamentoMalnutricao.findByPkIdTratamentoMalnutricao", query = "SELECT i FROM InterTratamentoMalnutricao i WHERE i.pkIdTratamentoMalnutricao = :pkIdTratamentoMalnutricao"),
    @NamedQuery(name = "InterTratamentoMalnutricao.findByData", query = "SELECT i FROM InterTratamentoMalnutricao i WHERE i.data = :data")})
public class InterTratamentoMalnutricao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tratamento_malnutricao", nullable = false)
    private Long pkIdTratamentoMalnutricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTratamentoMalnutricao")
    private List<InterTratamentoEspecifico> interTratamentoEspecificoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTratamentoMalnutricao")
    private List<InterAntropometria> interAntropometriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTratamentoMalnutricao")
    private List<InterTabelaVigilancia> interTabelaVigilanciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTratamentoMalnutricao")
    private List<InterDietaTerapeutica> interDietaTerapeuticaList;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTratamentoMalnutricao")
    private List<InterTratamentoSistematico> interTratamentoSistematicoList;

    public InterTratamentoMalnutricao() {
    }

    public InterTratamentoMalnutricao(Long pkIdTratamentoMalnutricao) {
        this.pkIdTratamentoMalnutricao = pkIdTratamentoMalnutricao;
    }

    public InterTratamentoMalnutricao(Long pkIdTratamentoMalnutricao, Date data) {
        this.pkIdTratamentoMalnutricao = pkIdTratamentoMalnutricao;
        this.data = data;
    }

    public Long getPkIdTratamentoMalnutricao() {
        return pkIdTratamentoMalnutricao;
    }

    public void setPkIdTratamentoMalnutricao(Long pkIdTratamentoMalnutricao) {
        this.pkIdTratamentoMalnutricao = pkIdTratamentoMalnutricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @XmlTransient
    public List<InterTratamentoEspecifico> getInterTratamentoEspecificoList() {
        return interTratamentoEspecificoList;
    }

    public void setInterTratamentoEspecificoList(List<InterTratamentoEspecifico> interTratamentoEspecificoList) {
        this.interTratamentoEspecificoList = interTratamentoEspecificoList;
    }

    @XmlTransient
    public List<InterAntropometria> getInterAntropometriaList() {
        return interAntropometriaList;
    }

    public void setInterAntropometriaList(List<InterAntropometria> interAntropometriaList) {
        this.interAntropometriaList = interAntropometriaList;
    }

    @XmlTransient
    public List<InterTabelaVigilancia> getInterTabelaVigilanciaList() {
        return interTabelaVigilanciaList;
    }

    public void setInterTabelaVigilanciaList(List<InterTabelaVigilancia> interTabelaVigilanciaList) {
        this.interTabelaVigilanciaList = interTabelaVigilanciaList;
    }

    @XmlTransient
    public List<InterDietaTerapeutica> getInterDietaTerapeuticaList() {
        return interDietaTerapeuticaList;
    }

    public void setInterDietaTerapeuticaList(List<InterDietaTerapeutica> interDietaTerapeuticaList) {
        this.interDietaTerapeuticaList = interDietaTerapeuticaList;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @XmlTransient
    public List<InterTratamentoSistematico> getInterTratamentoSistematicoList() {
        return interTratamentoSistematicoList;
    }

    public void setInterTratamentoSistematicoList(List<InterTratamentoSistematico> interTratamentoSistematicoList) {
        this.interTratamentoSistematicoList = interTratamentoSistematicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTratamentoMalnutricao != null ? pkIdTratamentoMalnutricao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterTratamentoMalnutricao)) {
            return false;
        }
        InterTratamentoMalnutricao other = (InterTratamentoMalnutricao) object;
        if ((this.pkIdTratamentoMalnutricao == null && other.pkIdTratamentoMalnutricao != null) || (this.pkIdTratamentoMalnutricao != null && !this.pkIdTratamentoMalnutricao.equals(other.pkIdTratamentoMalnutricao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterTratamentoMalnutricao[ pkIdTratamentoMalnutricao=" + pkIdTratamentoMalnutricao + " ]";
    }
    
}
