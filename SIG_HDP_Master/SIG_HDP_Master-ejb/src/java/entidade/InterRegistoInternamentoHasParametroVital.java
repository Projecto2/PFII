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
@Table(name = "inter_registo_internamento_has_parametro_vital", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterRegistoInternamentoHasParametroVital.findAll", query = "SELECT i FROM InterRegistoInternamentoHasParametroVital i"),
    @NamedQuery(name = "InterRegistoInternamentoHasParametroVital.findByPkIdRegistoInternamentoHasParametroVital", query = "SELECT i FROM InterRegistoInternamentoHasParametroVital i WHERE i.pkIdRegistoInternamentoHasParametroVital = :pkIdRegistoInternamentoHasParametroVital"),
    @NamedQuery(name = "InterRegistoInternamentoHasParametroVital.findByDataHora", query = "SELECT i FROM InterRegistoInternamentoHasParametroVital i WHERE i.dataHora = :dataHora")})
public class InterRegistoInternamentoHasParametroVital implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_registo_internamento_has_parametro_vital", nullable = false)
    private Long pkIdRegistoInternamentoHasParametroVital;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamentoHasParametroVital")
    private List<InterControloParametrosVitais> interControloParametrosVitaisList;
    @JoinColumn(name = "fk_id_hora_medicacao", referencedColumnName = "pk_id_hora_medicacao", nullable = false)
    @ManyToOne(optional = false)
    private InterHoraMedicacao fkIdHoraMedicacao;
    @JoinColumn(name = "fk_id_parametro_vital", referencedColumnName = "pk_id_parametro_vital", nullable = false)
    @ManyToOne(optional = false)
    private InterParametroVital fkIdParametroVital;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterRegistoInternamentoHasParametroVital() {
    }

    public InterRegistoInternamentoHasParametroVital(Long pkIdRegistoInternamentoHasParametroVital) {
        this.pkIdRegistoInternamentoHasParametroVital = pkIdRegistoInternamentoHasParametroVital;
    }

    public InterRegistoInternamentoHasParametroVital(Long pkIdRegistoInternamentoHasParametroVital, Date dataHora) {
        this.pkIdRegistoInternamentoHasParametroVital = pkIdRegistoInternamentoHasParametroVital;
        this.dataHora = dataHora;
    }

    public Long getPkIdRegistoInternamentoHasParametroVital() {
        return pkIdRegistoInternamentoHasParametroVital;
    }

    public void setPkIdRegistoInternamentoHasParametroVital(Long pkIdRegistoInternamentoHasParametroVital) {
        this.pkIdRegistoInternamentoHasParametroVital = pkIdRegistoInternamentoHasParametroVital;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    @XmlTransient
    public List<InterControloParametrosVitais> getInterControloParametrosVitaisList() {
        return interControloParametrosVitaisList;
    }

    public void setInterControloParametrosVitaisList(List<InterControloParametrosVitais> interControloParametrosVitaisList) {
        this.interControloParametrosVitaisList = interControloParametrosVitaisList;
    }

    public InterHoraMedicacao getFkIdHoraMedicacao() {
        return fkIdHoraMedicacao;
    }

    public void setFkIdHoraMedicacao(InterHoraMedicacao fkIdHoraMedicacao) {
        this.fkIdHoraMedicacao = fkIdHoraMedicacao;
    }

    public InterParametroVital getFkIdParametroVital() {
        return fkIdParametroVital;
    }

    public void setFkIdParametroVital(InterParametroVital fkIdParametroVital) {
        this.fkIdParametroVital = fkIdParametroVital;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRegistoInternamentoHasParametroVital != null ? pkIdRegistoInternamentoHasParametroVital.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterRegistoInternamentoHasParametroVital)) {
            return false;
        }
        InterRegistoInternamentoHasParametroVital other = (InterRegistoInternamentoHasParametroVital) object;
        if ((this.pkIdRegistoInternamentoHasParametroVital == null && other.pkIdRegistoInternamentoHasParametroVital != null) || (this.pkIdRegistoInternamentoHasParametroVital != null && !this.pkIdRegistoInternamentoHasParametroVital.equals(other.pkIdRegistoInternamentoHasParametroVital))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterRegistoInternamentoHasParametroVital[ pkIdRegistoInternamentoHasParametroVital=" + pkIdRegistoInternamentoHasParametroVital + " ]";
    }
    
}
