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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_controlo_mosquiteiro_reparticao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiControloMosquiteiroReparticao.findAll", query = "SELECT s FROM SupiControloMosquiteiroReparticao s"),
    @NamedQuery(name = "SupiControloMosquiteiroReparticao.findByQuantidadeRecebido", query = "SELECT s FROM SupiControloMosquiteiroReparticao s WHERE s.quantidadeRecebido = :quantidadeRecebido"),
    @NamedQuery(name = "SupiControloMosquiteiroReparticao.findByTotal", query = "SELECT s FROM SupiControloMosquiteiroReparticao s WHERE s.total = :total"),
    @NamedQuery(name = "SupiControloMosquiteiroReparticao.findByDataDestribuicao", query = "SELECT s FROM SupiControloMosquiteiroReparticao s WHERE s.dataDestribuicao = :dataDestribuicao"),
    @NamedQuery(name = "SupiControloMosquiteiroReparticao.findByPkIdControloMosquiteiro", query = "SELECT s FROM SupiControloMosquiteiroReparticao s WHERE s.pkIdControloMosquiteiro = :pkIdControloMosquiteiro")})
public class SupiControloMosquiteiroReparticao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade_recebido", nullable = false)
    private int quantidadeRecebido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total", nullable = false)
    private int total;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_destribuicao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataDestribuicao;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_controlo_mosquiteiro", nullable = false)
    private Integer pkIdControloMosquiteiro;

    public SupiControloMosquiteiroReparticao() {
    }

    public SupiControloMosquiteiroReparticao(Integer pkIdControloMosquiteiro) {
        this.pkIdControloMosquiteiro = pkIdControloMosquiteiro;
    }

    public SupiControloMosquiteiroReparticao(Integer pkIdControloMosquiteiro, int quantidadeRecebido, int total, Date dataDestribuicao) {
        this.pkIdControloMosquiteiro = pkIdControloMosquiteiro;
        this.quantidadeRecebido = quantidadeRecebido;
        this.total = total;
        this.dataDestribuicao = dataDestribuicao;
    }

    public int getQuantidadeRecebido() {
        return quantidadeRecebido;
    }

    public void setQuantidadeRecebido(int quantidadeRecebido) {
        this.quantidadeRecebido = quantidadeRecebido;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getDataDestribuicao() {
        return dataDestribuicao;
    }

    public void setDataDestribuicao(Date dataDestribuicao) {
        this.dataDestribuicao = dataDestribuicao;
    }

    public Integer getPkIdControloMosquiteiro() {
        return pkIdControloMosquiteiro;
    }

    public void setPkIdControloMosquiteiro(Integer pkIdControloMosquiteiro) {
        this.pkIdControloMosquiteiro = pkIdControloMosquiteiro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdControloMosquiteiro != null ? pkIdControloMosquiteiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiControloMosquiteiroReparticao)) {
            return false;
        }
        SupiControloMosquiteiroReparticao other = (SupiControloMosquiteiroReparticao) object;
        if ((this.pkIdControloMosquiteiro == null && other.pkIdControloMosquiteiro != null) || (this.pkIdControloMosquiteiro != null && !this.pkIdControloMosquiteiro.equals(other.pkIdControloMosquiteiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiControloMosquiteiroReparticao[ pkIdControloMosquiteiro=" + pkIdControloMosquiteiro + " ]";
    }
    
}
