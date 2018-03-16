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
@Table(name = "diag_tipo_amostra", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipoAmostra.findAll", query = "SELECT d FROM DiagTipoAmostra d"),
    @NamedQuery(name = "DiagTipoAmostra.findByPkIdTipoAmostra", query = "SELECT d FROM DiagTipoAmostra d WHERE d.pkIdTipoAmostra = :pkIdTipoAmostra"),
    @NamedQuery(name = "DiagTipoAmostra.findByDescricaoTipoAmostra", query = "SELECT d FROM DiagTipoAmostra d WHERE d.descricaoTipoAmostra = :descricaoTipoAmostra")})
public class DiagTipoAmostra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_amostra", nullable = false)
    private Integer pkIdTipoAmostra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao_tipo_amostra", nullable = false, length = 45)
    private String descricaoTipoAmostra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoAmostra")
    private List<DiagAmostra> diagAmostraList;

    public DiagTipoAmostra() {
    }

    public DiagTipoAmostra(Integer pkIdTipoAmostra) {
        this.pkIdTipoAmostra = pkIdTipoAmostra;
    }

    public DiagTipoAmostra(Integer pkIdTipoAmostra, String descricaoTipoAmostra) {
        this.pkIdTipoAmostra = pkIdTipoAmostra;
        this.descricaoTipoAmostra = descricaoTipoAmostra;
    }

    public Integer getPkIdTipoAmostra() {
        return pkIdTipoAmostra;
    }

    public void setPkIdTipoAmostra(Integer pkIdTipoAmostra) {
        this.pkIdTipoAmostra = pkIdTipoAmostra;
    }

    public String getDescricaoTipoAmostra() {
        return descricaoTipoAmostra;
    }

    public void setDescricaoTipoAmostra(String descricaoTipoAmostra) {
        this.descricaoTipoAmostra = descricaoTipoAmostra;
    }

    @XmlTransient
    public List<DiagAmostra> getDiagAmostraList() {
        return diagAmostraList;
    }

    public void setDiagAmostraList(List<DiagAmostra> diagAmostraList) {
        this.diagAmostraList = diagAmostraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoAmostra != null ? pkIdTipoAmostra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipoAmostra)) {
            return false;
        }
        DiagTipoAmostra other = (DiagTipoAmostra) object;
        if ((this.pkIdTipoAmostra == null && other.pkIdTipoAmostra != null) || (this.pkIdTipoAmostra != null && !this.pkIdTipoAmostra.equals(other.pkIdTipoAmostra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipoAmostra[ pkIdTipoAmostra=" + pkIdTipoAmostra + " ]";
    }
    
}
