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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_guia_transferencia_doentes", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findAll", query = "SELECT i FROM InterGuiaTransferenciaDoentes i"),
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findByPkIdGuiaTransferenciaDoentes", query = "SELECT i FROM InterGuiaTransferenciaDoentes i WHERE i.pkIdGuiaTransferenciaDoentes = :pkIdGuiaTransferenciaDoentes"),
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findByDestino", query = "SELECT i FROM InterGuiaTransferenciaDoentes i WHERE i.destino = :destino"),
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findByQueixasPrincipais", query = "SELECT i FROM InterGuiaTransferenciaDoentes i WHERE i.queixasPrincipais = :queixasPrincipais"),
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findByNumeroGuia", query = "SELECT i FROM InterGuiaTransferenciaDoentes i WHERE i.numeroGuia = :numeroGuia"),
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findByDataHora", query = "SELECT i FROM InterGuiaTransferenciaDoentes i WHERE i.dataHora = :dataHora"),
    @NamedQuery(name = "InterGuiaTransferenciaDoentes.findByRecomendacoes", query = "SELECT i FROM InterGuiaTransferenciaDoentes i WHERE i.recomendacoes = :recomendacoes")})
public class InterGuiaTransferenciaDoentes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_guia_transferencia_doentes", nullable = false)
    private Integer pkIdGuiaTransferenciaDoentes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "destino", nullable = false, length = 100)
    private String destino;
    @Size(max = 200)
    @Column(name = "queixas_principais", length = 200)
    private String queixasPrincipais;
    @Size(max = 100)
    @Column(name = "numero_guia", length = 100)
    private String numeroGuia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Size(max = 200)
    @Column(name = "recomendacoes", length = 200)
    private String recomendacoes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdGuiaTransferenciaDoentes")
    private List<InterGuiaTransferenciaHasMotivos> interGuiaTransferenciaHasMotivosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdGuiaTransferenciaDoentes")
    private List<InterGuiaTransferenciaHasCriteriosClinicos> interGuiaTransferenciaHasCriteriosClinicosList;
    @JoinColumn(name = "fk_id_meio_transporte", referencedColumnName = "pk_id_meio_transporte")
    @ManyToOne
    private InterMeioTransporte fkIdMeioTransporte;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario_assistente", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioAssistente;
    @JoinColumn(name = "fk_id_funcionario_chefe_equipe", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionarioChefeEquipe;

    public InterGuiaTransferenciaDoentes() {
    }

    public InterGuiaTransferenciaDoentes(Integer pkIdGuiaTransferenciaDoentes) {
        this.pkIdGuiaTransferenciaDoentes = pkIdGuiaTransferenciaDoentes;
    }

    public InterGuiaTransferenciaDoentes(Integer pkIdGuiaTransferenciaDoentes, String destino, Date dataHora) {
        this.pkIdGuiaTransferenciaDoentes = pkIdGuiaTransferenciaDoentes;
        this.destino = destino;
        this.dataHora = dataHora;
    }

    public Integer getPkIdGuiaTransferenciaDoentes() {
        return pkIdGuiaTransferenciaDoentes;
    }

    public void setPkIdGuiaTransferenciaDoentes(Integer pkIdGuiaTransferenciaDoentes) {
        this.pkIdGuiaTransferenciaDoentes = pkIdGuiaTransferenciaDoentes;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getQueixasPrincipais() {
        return queixasPrincipais;
    }

    public void setQueixasPrincipais(String queixasPrincipais) {
        this.queixasPrincipais = queixasPrincipais;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(String recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaHasMotivos> getInterGuiaTransferenciaHasMotivosList() {
        return interGuiaTransferenciaHasMotivosList;
    }

    public void setInterGuiaTransferenciaHasMotivosList(List<InterGuiaTransferenciaHasMotivos> interGuiaTransferenciaHasMotivosList) {
        this.interGuiaTransferenciaHasMotivosList = interGuiaTransferenciaHasMotivosList;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaHasCriteriosClinicos> getInterGuiaTransferenciaHasCriteriosClinicosList() {
        return interGuiaTransferenciaHasCriteriosClinicosList;
    }

    public void setInterGuiaTransferenciaHasCriteriosClinicosList(List<InterGuiaTransferenciaHasCriteriosClinicos> interGuiaTransferenciaHasCriteriosClinicosList) {
        this.interGuiaTransferenciaHasCriteriosClinicosList = interGuiaTransferenciaHasCriteriosClinicosList;
    }

    public InterMeioTransporte getFkIdMeioTransporte() {
        return fkIdMeioTransporte;
    }

    public void setFkIdMeioTransporte(InterMeioTransporte fkIdMeioTransporte) {
        this.fkIdMeioTransporte = fkIdMeioTransporte;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public RhFuncionario getFkIdFuncionarioAssistente() {
        return fkIdFuncionarioAssistente;
    }

    public void setFkIdFuncionarioAssistente(RhFuncionario fkIdFuncionarioAssistente) {
        this.fkIdFuncionarioAssistente = fkIdFuncionarioAssistente;
    }

    public RhFuncionario getFkIdFuncionarioChefeEquipe() {
        return fkIdFuncionarioChefeEquipe;
    }

    public void setFkIdFuncionarioChefeEquipe(RhFuncionario fkIdFuncionarioChefeEquipe) {
        this.fkIdFuncionarioChefeEquipe = fkIdFuncionarioChefeEquipe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdGuiaTransferenciaDoentes != null ? pkIdGuiaTransferenciaDoentes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterGuiaTransferenciaDoentes)) {
            return false;
        }
        InterGuiaTransferenciaDoentes other = (InterGuiaTransferenciaDoentes) object;
        if ((this.pkIdGuiaTransferenciaDoentes == null && other.pkIdGuiaTransferenciaDoentes != null) || (this.pkIdGuiaTransferenciaDoentes != null && !this.pkIdGuiaTransferenciaDoentes.equals(other.pkIdGuiaTransferenciaDoentes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterGuiaTransferenciaDoentes[ pkIdGuiaTransferenciaDoentes=" + pkIdGuiaTransferenciaDoentes + " ]";
    }
    
}
