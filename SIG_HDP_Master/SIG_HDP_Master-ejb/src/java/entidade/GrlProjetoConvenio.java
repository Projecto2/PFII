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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_projeto_convenio", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlProjetoConvenio.findAll", query = "SELECT g FROM GrlProjetoConvenio g"),
    @NamedQuery(name = "GrlProjetoConvenio.findByPkIdProjetoConvenio", query = "SELECT g FROM GrlProjetoConvenio g WHERE g.pkIdProjetoConvenio = :pkIdProjetoConvenio"),
    @NamedQuery(name = "GrlProjetoConvenio.findByDescricaoProjeto", query = "SELECT g FROM GrlProjetoConvenio g WHERE g.descricaoProjeto = :descricaoProjeto"),
    @NamedQuery(name = "GrlProjetoConvenio.findByEstadoProjetoConvenio", query = "SELECT g FROM GrlProjetoConvenio g WHERE g.estadoProjetoConvenio = :estadoProjetoConvenio")})
public class GrlProjetoConvenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_projeto_convenio", nullable = false)
    private Integer pkIdProjetoConvenio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descricao_projeto", nullable = false, length = 200)
    private String descricaoProjeto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_projeto_convenio", nullable = false)
    private boolean estadoProjetoConvenio;
    @OneToMany(mappedBy = "fkIdProjetoConvenio")
    private List<FinPagamentoConvenio> finPagamentoConvenioList;
    @JoinColumn(name = "fk_id_convenio", referencedColumnName = "pk_id_convenio")
    @ManyToOne
    private GrlConvenio fkIdConvenio;

    public GrlProjetoConvenio() {
    }

    public GrlProjetoConvenio(Integer pkIdProjetoConvenio) {
        this.pkIdProjetoConvenio = pkIdProjetoConvenio;
    }

    public GrlProjetoConvenio(Integer pkIdProjetoConvenio, String descricaoProjeto, boolean estadoProjetoConvenio) {
        this.pkIdProjetoConvenio = pkIdProjetoConvenio;
        this.descricaoProjeto = descricaoProjeto;
        this.estadoProjetoConvenio = estadoProjetoConvenio;
    }

    public Integer getPkIdProjetoConvenio() {
        return pkIdProjetoConvenio;
    }

    public void setPkIdProjetoConvenio(Integer pkIdProjetoConvenio) {
        this.pkIdProjetoConvenio = pkIdProjetoConvenio;
    }

    public String getDescricaoProjeto() {
        return descricaoProjeto;
    }

    public void setDescricaoProjeto(String descricaoProjeto) {
        this.descricaoProjeto = descricaoProjeto;
    }

    public boolean getEstadoProjetoConvenio() {
        return estadoProjetoConvenio;
    }

    public void setEstadoProjetoConvenio(boolean estadoProjetoConvenio) {
        this.estadoProjetoConvenio = estadoProjetoConvenio;
    }

    @XmlTransient
    public List<FinPagamentoConvenio> getFinPagamentoConvenioList() {
        return finPagamentoConvenioList;
    }

    public void setFinPagamentoConvenioList(List<FinPagamentoConvenio> finPagamentoConvenioList) {
        this.finPagamentoConvenioList = finPagamentoConvenioList;
    }

    public GrlConvenio getFkIdConvenio() {
        return fkIdConvenio;
    }

    public void setFkIdConvenio(GrlConvenio fkIdConvenio) {
        this.fkIdConvenio = fkIdConvenio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProjetoConvenio != null ? pkIdProjetoConvenio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlProjetoConvenio)) {
            return false;
        }
        GrlProjetoConvenio other = (GrlProjetoConvenio) object;
        if ((this.pkIdProjetoConvenio == null && other.pkIdProjetoConvenio != null) || (this.pkIdProjetoConvenio != null && !this.pkIdProjetoConvenio.equals(other.pkIdProjetoConvenio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlProjetoConvenio[ pkIdProjetoConvenio=" + pkIdProjetoConvenio + " ]";
    }
    
}
