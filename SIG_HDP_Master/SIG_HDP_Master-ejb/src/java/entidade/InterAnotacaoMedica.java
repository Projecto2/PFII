/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_anotacao_medica", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterAnotacaoMedica.findAll", query = "SELECT i FROM InterAnotacaoMedica i"),
    @NamedQuery(name = "InterAnotacaoMedica.findByPkIdAnotacaoMedica", query = "SELECT i FROM InterAnotacaoMedica i WHERE i.pkIdAnotacaoMedica = :pkIdAnotacaoMedica"),
    @NamedQuery(name = "InterAnotacaoMedica.findByDescricao", query = "SELECT i FROM InterAnotacaoMedica i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterAnotacaoMedica.findByDataHora", query = "SELECT i FROM InterAnotacaoMedica i WHERE i.dataHora = :dataHora"),
    @NamedQuery(name = "InterAnotacaoMedica.findByData", query = "SELECT i FROM InterAnotacaoMedica i WHERE i.data = :data")})
public class InterAnotacaoMedica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_anotacao_medica", nullable = false)
    private Integer pkIdAnotacaoMedica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterAnotacaoMedica() {
    }

    public InterAnotacaoMedica(Integer pkIdAnotacaoMedica) {
        this.pkIdAnotacaoMedica = pkIdAnotacaoMedica;
    }

    public InterAnotacaoMedica(Integer pkIdAnotacaoMedica, String descricao) {
        this.pkIdAnotacaoMedica = pkIdAnotacaoMedica;
        this.descricao = descricao;
    }

    public Integer getPkIdAnotacaoMedica() {
        return pkIdAnotacaoMedica;
    }

    public void setPkIdAnotacaoMedica(Integer pkIdAnotacaoMedica) {
        this.pkIdAnotacaoMedica = pkIdAnotacaoMedica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
        hash += (pkIdAnotacaoMedica != null ? pkIdAnotacaoMedica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterAnotacaoMedica)) {
            return false;
        }
        InterAnotacaoMedica other = (InterAnotacaoMedica) object;
        if ((this.pkIdAnotacaoMedica == null && other.pkIdAnotacaoMedica != null) || (this.pkIdAnotacaoMedica != null && !this.pkIdAnotacaoMedica.equals(other.pkIdAnotacaoMedica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterAnotacaoMedica[ pkIdAnotacaoMedica=" + pkIdAnotacaoMedica + " ]";
    }
    
}
