/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_servico_efetuado", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_servico_solicitado"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsServicoEfetuado.findAll", query = "SELECT a FROM AdmsServicoEfetuado a"),
    @NamedQuery(name = "AdmsServicoEfetuado.findByPkIdServicoEfetuado", query = "SELECT a FROM AdmsServicoEfetuado a WHERE a.pkIdServicoEfetuado = :pkIdServicoEfetuado"),
    @NamedQuery(name = "AdmsServicoEfetuado.findByDescricaoTabelaBusca", query = "SELECT a FROM AdmsServicoEfetuado a WHERE a.descricaoTabelaBusca = :descricaoTabelaBusca"),
    @NamedQuery(name = "AdmsServicoEfetuado.findByCodigoTabelaBusca", query = "SELECT a FROM AdmsServicoEfetuado a WHERE a.codigoTabelaBusca = :codigoTabelaBusca"),
    @NamedQuery(name = "AdmsServicoEfetuado.findByDataEfetuada", query = "SELECT a FROM AdmsServicoEfetuado a WHERE a.dataEfetuada = :dataEfetuada")})
public class AdmsServicoEfetuado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_servico_efetuado", nullable = false)
    private Long pkIdServicoEfetuado;
    @Size(max = 150)
    @Column(name = "descricao_tabela_busca", length = 150)
    private String descricaoTabelaBusca;
    @Column(name = "codigo_tabela_busca")
    private BigInteger codigoTabelaBusca;
    @Column(name = "data_efetuada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEfetuada;
    @OneToMany(mappedBy = "fkIdServicoEfetuado")
    private List<AdmsSolicitacao> admsSolicitacaoList;
    @JoinColumn(name = "fk_id_servico_solicitado", referencedColumnName = "pk_id_servico_solicitado")
    @OneToOne
    private AdmsServicoSolicitado fkIdServicoSolicitado;

    public AdmsServicoEfetuado() {
    }

    public AdmsServicoEfetuado(Long pkIdServicoEfetuado) {
        this.pkIdServicoEfetuado = pkIdServicoEfetuado;
    }

    public Long getPkIdServicoEfetuado() {
        return pkIdServicoEfetuado;
    }

    public void setPkIdServicoEfetuado(Long pkIdServicoEfetuado) {
        this.pkIdServicoEfetuado = pkIdServicoEfetuado;
    }

    public String getDescricaoTabelaBusca() {
        return descricaoTabelaBusca;
    }

    public void setDescricaoTabelaBusca(String descricaoTabelaBusca) {
        this.descricaoTabelaBusca = descricaoTabelaBusca;
    }

    public BigInteger getCodigoTabelaBusca() {
        return codigoTabelaBusca;
    }

    public void setCodigoTabelaBusca(BigInteger codigoTabelaBusca) {
        this.codigoTabelaBusca = codigoTabelaBusca;
    }

    public Date getDataEfetuada() {
        return dataEfetuada;
    }

    public void setDataEfetuada(Date dataEfetuada) {
        this.dataEfetuada = dataEfetuada;
    }

    @XmlTransient
    public List<AdmsSolicitacao> getAdmsSolicitacaoList() {
        return admsSolicitacaoList;
    }

    public void setAdmsSolicitacaoList(List<AdmsSolicitacao> admsSolicitacaoList) {
        this.admsSolicitacaoList = admsSolicitacaoList;
    }

    public AdmsServicoSolicitado getFkIdServicoSolicitado() {
        return fkIdServicoSolicitado;
    }

    public void setFkIdServicoSolicitado(AdmsServicoSolicitado fkIdServicoSolicitado) {
        this.fkIdServicoSolicitado = fkIdServicoSolicitado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdServicoEfetuado != null ? pkIdServicoEfetuado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsServicoEfetuado)) {
            return false;
        }
        AdmsServicoEfetuado other = (AdmsServicoEfetuado) object;
        if ((this.pkIdServicoEfetuado == null && other.pkIdServicoEfetuado != null) || (this.pkIdServicoEfetuado != null && !this.pkIdServicoEfetuado.equals(other.pkIdServicoEfetuado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsServicoEfetuado[ pkIdServicoEfetuado=" + pkIdServicoEfetuado + " ]";
    }
    
}
