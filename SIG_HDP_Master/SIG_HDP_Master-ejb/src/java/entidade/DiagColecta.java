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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_colecta", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagColecta.findAll", query = "SELECT d FROM DiagColecta d"),
    @NamedQuery(name = "DiagColecta.findByPkIdColecta", query = "SELECT d FROM DiagColecta d WHERE d.pkIdColecta = :pkIdColecta"),
    @NamedQuery(name = "DiagColecta.findByDataColecta", query = "SELECT d FROM DiagColecta d WHERE d.dataColecta = :dataColecta")})
public class DiagColecta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_colecta", nullable = false)
    private Integer pkIdColecta;
    @Column(name = "data_colecta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataColecta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdColecta")
    private List<DiagAmostra> diagAmostraList;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public DiagColecta() {
    }

    public DiagColecta(Integer pkIdColecta) {
        this.pkIdColecta = pkIdColecta;
    }

    public Integer getPkIdColecta() {
        return pkIdColecta;
    }

    public void setPkIdColecta(Integer pkIdColecta) {
        this.pkIdColecta = pkIdColecta;
    }

    public Date getDataColecta() {
        return dataColecta;
    }

    public void setDataColecta(Date dataColecta) {
        this.dataColecta = dataColecta;
    }

    @XmlTransient
    public List<DiagAmostra> getDiagAmostraList() {
        return diagAmostraList;
    }

    public void setDiagAmostraList(List<DiagAmostra> diagAmostraList) {
        this.diagAmostraList = diagAmostraList;
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
        hash += (pkIdColecta != null ? pkIdColecta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagColecta)) {
            return false;
        }
        DiagColecta other = (DiagColecta) object;
        if ((this.pkIdColecta == null && other.pkIdColecta != null) || (this.pkIdColecta != null && !this.pkIdColecta.equals(other.pkIdColecta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagColecta[ pkIdColecta=" + pkIdColecta + " ]";
    }
    
}
