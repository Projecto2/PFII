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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_desconhecido", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsDesconhecido.findAll", query = "SELECT a FROM AdmsDesconhecido a"),
    @NamedQuery(name = "AdmsDesconhecido.findByPkIdDesconhecido", query = "SELECT a FROM AdmsDesconhecido a WHERE a.pkIdDesconhecido = :pkIdDesconhecido"),
    @NamedQuery(name = "AdmsDesconhecido.findByObservacao", query = "SELECT a FROM AdmsDesconhecido a WHERE a.observacao = :observacao")})
public class AdmsDesconhecido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_desconhecido", nullable = false)
    private Long pkIdDesconhecido;
    @Size(max = 500)
    @Column(name = "observacao", length = 500)
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pkIdDesconhecido")
    private List<AdmsAtividadeDesconhecido> admsAtividadeDesconhecidoList;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente")
    @ManyToOne
    private AdmsPaciente fkIdPaciente;

    public AdmsDesconhecido() {
    }

    public AdmsDesconhecido(Long pkIdDesconhecido) {
        this.pkIdDesconhecido = pkIdDesconhecido;
    }

    public Long getPkIdDesconhecido() {
        return pkIdDesconhecido;
    }

    public void setPkIdDesconhecido(Long pkIdDesconhecido) {
        this.pkIdDesconhecido = pkIdDesconhecido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @XmlTransient
    public List<AdmsAtividadeDesconhecido> getAdmsAtividadeDesconhecidoList() {
        return admsAtividadeDesconhecidoList;
    }

    public void setAdmsAtividadeDesconhecidoList(List<AdmsAtividadeDesconhecido> admsAtividadeDesconhecidoList) {
        this.admsAtividadeDesconhecidoList = admsAtividadeDesconhecidoList;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDesconhecido != null ? pkIdDesconhecido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsDesconhecido)) {
            return false;
        }
        AdmsDesconhecido other = (AdmsDesconhecido) object;
        if ((this.pkIdDesconhecido == null && other.pkIdDesconhecido != null) || (this.pkIdDesconhecido != null && !this.pkIdDesconhecido.equals(other.pkIdDesconhecido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsDesconhecido[ pkIdDesconhecido=" + pkIdDesconhecido + " ]";
    }
    
}
