/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "diag_tipo_resultado_exame", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipoResultadoExame.findAll", query = "SELECT d FROM DiagTipoResultadoExame d"),
    @NamedQuery(name = "DiagTipoResultadoExame.findByPkIdTipoResultadoExame", query = "SELECT d FROM DiagTipoResultadoExame d WHERE d.pkIdTipoResultadoExame = :pkIdTipoResultadoExame"),
    @NamedQuery(name = "DiagTipoResultadoExame.findByDescricaoTipoResultadoExame", query = "SELECT d FROM DiagTipoResultadoExame d WHERE d.descricaoTipoResultadoExame = :descricaoTipoResultadoExame")})
public class DiagTipoResultadoExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_resultado_exame", nullable = false)
    private Integer pkIdTipoResultadoExame;
    @Size(max = 45)
    @Column(name = "descricao_tipo_resultado_exame", length = 45)
    private String descricaoTipoResultadoExame;
    @OneToMany(mappedBy = "fkIdTipoResultado")
    private List<DiagExame> diagExameList;

    public DiagTipoResultadoExame() {
    }

    public DiagTipoResultadoExame(Integer pkIdTipoResultadoExame) {
        this.pkIdTipoResultadoExame = pkIdTipoResultadoExame;
    }

    public Integer getPkIdTipoResultadoExame() {
        return pkIdTipoResultadoExame;
    }

    public void setPkIdTipoResultadoExame(Integer pkIdTipoResultadoExame) {
        this.pkIdTipoResultadoExame = pkIdTipoResultadoExame;
    }

    public String getDescricaoTipoResultadoExame() {
        return descricaoTipoResultadoExame;
    }

    public void setDescricaoTipoResultadoExame(String descricaoTipoResultadoExame) {
        this.descricaoTipoResultadoExame = descricaoTipoResultadoExame;
    }

    @XmlTransient
    public List<DiagExame> getDiagExameList() {
        return diagExameList;
    }

    public void setDiagExameList(List<DiagExame> diagExameList) {
        this.diagExameList = diagExameList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoResultadoExame != null ? pkIdTipoResultadoExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipoResultadoExame)) {
            return false;
        }
        DiagTipoResultadoExame other = (DiagTipoResultadoExame) object;
        if ((this.pkIdTipoResultadoExame == null && other.pkIdTipoResultadoExame != null) || (this.pkIdTipoResultadoExame != null && !this.pkIdTipoResultadoExame.equals(other.pkIdTipoResultadoExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipoResultadoExame[ pkIdTipoResultadoExame=" + pkIdTipoResultadoExame + " ]";
    }
    
}
