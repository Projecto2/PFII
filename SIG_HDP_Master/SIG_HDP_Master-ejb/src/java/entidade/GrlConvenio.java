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
@Table(name = "grl_convenio", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlConvenio.findAll", query = "SELECT g FROM GrlConvenio g"),
    @NamedQuery(name = "GrlConvenio.findByPkIdConvenio", query = "SELECT g FROM GrlConvenio g WHERE g.pkIdConvenio = :pkIdConvenio"),
    @NamedQuery(name = "GrlConvenio.findByNomeConvenio", query = "SELECT g FROM GrlConvenio g WHERE g.nomeConvenio = :nomeConvenio"),
    @NamedQuery(name = "GrlConvenio.findByEstadoConvenio", query = "SELECT g FROM GrlConvenio g WHERE g.estadoConvenio = :estadoConvenio")})
public class GrlConvenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_convenio", nullable = false)
    private Integer pkIdConvenio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_convenio", nullable = false, length = 50)
    private String nomeConvenio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_convenio", nullable = false)
    private boolean estadoConvenio;
    @OneToMany(mappedBy = "fkIdConvenio")
    private List<FinPagamentoConvenio> finPagamentoConvenioList;
    @OneToMany(mappedBy = "fkIdConvenio")
    private List<GrlProjetoConvenio> grlProjetoConvenioList;
    @JoinColumn(name = "fk_id_instituicao", referencedColumnName = "pk_id_instituicao", nullable = false)
    @ManyToOne(optional = false)
    private GrlInstituicao fkIdInstituicao;

    public GrlConvenio() {
    }

    public GrlConvenio(Integer pkIdConvenio) {
        this.pkIdConvenio = pkIdConvenio;
    }

    public GrlConvenio(Integer pkIdConvenio, String nomeConvenio, boolean estadoConvenio) {
        this.pkIdConvenio = pkIdConvenio;
        this.nomeConvenio = nomeConvenio;
        this.estadoConvenio = estadoConvenio;
    }

    public Integer getPkIdConvenio() {
        return pkIdConvenio;
    }

    public void setPkIdConvenio(Integer pkIdConvenio) {
        this.pkIdConvenio = pkIdConvenio;
    }

    public String getNomeConvenio() {
        return nomeConvenio;
    }

    public void setNomeConvenio(String nomeConvenio) {
        this.nomeConvenio = nomeConvenio;
    }

    public boolean getEstadoConvenio() {
        return estadoConvenio;
    }

    public void setEstadoConvenio(boolean estadoConvenio) {
        this.estadoConvenio = estadoConvenio;
    }

    @XmlTransient
    public List<FinPagamentoConvenio> getFinPagamentoConvenioList() {
        return finPagamentoConvenioList;
    }

    public void setFinPagamentoConvenioList(List<FinPagamentoConvenio> finPagamentoConvenioList) {
        this.finPagamentoConvenioList = finPagamentoConvenioList;
    }

    @XmlTransient
    public List<GrlProjetoConvenio> getGrlProjetoConvenioList() {
        return grlProjetoConvenioList;
    }

    public void setGrlProjetoConvenioList(List<GrlProjetoConvenio> grlProjetoConvenioList) {
        this.grlProjetoConvenioList = grlProjetoConvenioList;
    }

    public GrlInstituicao getFkIdInstituicao() {
        return fkIdInstituicao;
    }

    public void setFkIdInstituicao(GrlInstituicao fkIdInstituicao) {
        this.fkIdInstituicao = fkIdInstituicao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConvenio != null ? pkIdConvenio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlConvenio)) {
            return false;
        }
        GrlConvenio other = (GrlConvenio) object;
        if ((this.pkIdConvenio == null && other.pkIdConvenio != null) || (this.pkIdConvenio != null && !this.pkIdConvenio.equals(other.pkIdConvenio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlConvenio[ pkIdConvenio=" + pkIdConvenio + " ]";
    }
    
}
