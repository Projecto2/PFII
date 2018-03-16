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
@Table(name = "adms_categoria_servico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsCategoriaServico.findAll", query = "SELECT a FROM AdmsCategoriaServico a"),
    @NamedQuery(name = "AdmsCategoriaServico.findByPkIdCategoriaServico", query = "SELECT a FROM AdmsCategoriaServico a WHERE a.pkIdCategoriaServico = :pkIdCategoriaServico"),
    @NamedQuery(name = "AdmsCategoriaServico.findByEstadoCategoriaServico", query = "SELECT a FROM AdmsCategoriaServico a WHERE a.estadoCategoriaServico = :estadoCategoriaServico"),
    @NamedQuery(name = "AdmsCategoriaServico.findByDescricaoCategoriaServico", query = "SELECT a FROM AdmsCategoriaServico a WHERE a.descricaoCategoriaServico = :descricaoCategoriaServico"),
    @NamedQuery(name = "AdmsCategoriaServico.findByTipoUnico", query = "SELECT a FROM AdmsCategoriaServico a WHERE a.tipoUnico = :tipoUnico")})
public class AdmsCategoriaServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_categoria_servico", nullable = false)
    private Integer pkIdCategoriaServico;
    @Column(name = "estado_categoria_servico")
    private Boolean estadoCategoriaServico;
    @Size(max = 150)
    @Column(name = "descricao_categoria_servico", length = 150)
    private String descricaoCategoriaServico;
    @Column(name = "tipo_unico")
    private Boolean tipoUnico;
    @JoinColumn(name = "fk_id_servico", referencedColumnName = "pk_id_servico")
    @ManyToOne
    private AdmsServico fkIdServico;
    @OneToMany(mappedBy = "fkIdCategoriaServico")
    private List<FinPrecoCategoriaServico> finPrecoCategoriaServicoList;

    public AdmsCategoriaServico() {
    }

    public AdmsCategoriaServico(Integer pkIdCategoriaServico) {
        this.pkIdCategoriaServico = pkIdCategoriaServico;
    }

    public Integer getPkIdCategoriaServico() {
        return pkIdCategoriaServico;
    }

    public void setPkIdCategoriaServico(Integer pkIdCategoriaServico) {
        this.pkIdCategoriaServico = pkIdCategoriaServico;
    }

    public Boolean getEstadoCategoriaServico() {
        return estadoCategoriaServico;
    }

    public void setEstadoCategoriaServico(Boolean estadoCategoriaServico) {
        this.estadoCategoriaServico = estadoCategoriaServico;
    }

    public String getDescricaoCategoriaServico() {
        return descricaoCategoriaServico;
    }

    public void setDescricaoCategoriaServico(String descricaoCategoriaServico) {
        this.descricaoCategoriaServico = descricaoCategoriaServico;
    }

    public Boolean getTipoUnico() {
        return tipoUnico;
    }

    public void setTipoUnico(Boolean tipoUnico) {
        this.tipoUnico = tipoUnico;
    }

    public AdmsServico getFkIdServico() {
        return fkIdServico;
    }

    public void setFkIdServico(AdmsServico fkIdServico) {
        this.fkIdServico = fkIdServico;
    }

    @XmlTransient
    public List<FinPrecoCategoriaServico> getFinPrecoCategoriaServicoList() {
        return finPrecoCategoriaServicoList;
    }

    public void setFinPrecoCategoriaServicoList(List<FinPrecoCategoriaServico> finPrecoCategoriaServicoList) {
        this.finPrecoCategoriaServicoList = finPrecoCategoriaServicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCategoriaServico != null ? pkIdCategoriaServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsCategoriaServico)) {
            return false;
        }
        AdmsCategoriaServico other = (AdmsCategoriaServico) object;
        if ((this.pkIdCategoriaServico == null && other.pkIdCategoriaServico != null) || (this.pkIdCategoriaServico != null && !this.pkIdCategoriaServico.equals(other.pkIdCategoriaServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsCategoriaServico[ pkIdCategoriaServico=" + pkIdCategoriaServico + " ]";
    }
    
}
