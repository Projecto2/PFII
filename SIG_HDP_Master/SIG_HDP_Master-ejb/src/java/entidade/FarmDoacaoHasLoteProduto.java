/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_doacao_has_lote_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmDoacaoHasLoteProduto.findAll", query = "SELECT f FROM FarmDoacaoHasLoteProduto f"),
    @NamedQuery(name = "FarmDoacaoHasLoteProduto.findByPkIdDoacaoHasLoteProduto", query = "SELECT f FROM FarmDoacaoHasLoteProduto f WHERE f.pkIdDoacaoHasLoteProduto = :pkIdDoacaoHasLoteProduto"),
    @NamedQuery(name = "FarmDoacaoHasLoteProduto.findByQuantidade", query = "SELECT f FROM FarmDoacaoHasLoteProduto f WHERE f.quantidade = :quantidade")})
public class FarmDoacaoHasLoteProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_doacao_has_lote_produto", nullable = false)
    private Integer pkIdDoacaoHasLoteProduto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    @JoinColumn(name = "fk_id_doacao", referencedColumnName = "pk_id_doacao", nullable = false)
    @ManyToOne(optional = false)
    private FarmDoacao fkIdDoacao;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmLoteProduto fkIdLoteProduto;

    public FarmDoacaoHasLoteProduto() {
    }

    public FarmDoacaoHasLoteProduto(Integer pkIdDoacaoHasLoteProduto) {
        this.pkIdDoacaoHasLoteProduto = pkIdDoacaoHasLoteProduto;
    }

    public FarmDoacaoHasLoteProduto(Integer pkIdDoacaoHasLoteProduto, int quantidade) {
        this.pkIdDoacaoHasLoteProduto = pkIdDoacaoHasLoteProduto;
        this.quantidade = quantidade;
    }

    public Integer getPkIdDoacaoHasLoteProduto() {
        return pkIdDoacaoHasLoteProduto;
    }

    public void setPkIdDoacaoHasLoteProduto(Integer pkIdDoacaoHasLoteProduto) {
        this.pkIdDoacaoHasLoteProduto = pkIdDoacaoHasLoteProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public FarmDoacao getFkIdDoacao() {
        return fkIdDoacao;
    }

    public void setFkIdDoacao(FarmDoacao fkIdDoacao) {
        this.fkIdDoacao = fkIdDoacao;
    }

    public FarmLoteProduto getFkIdLoteProduto() {
        return fkIdLoteProduto;
    }

    public void setFkIdLoteProduto(FarmLoteProduto fkIdLoteProduto) {
        this.fkIdLoteProduto = fkIdLoteProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDoacaoHasLoteProduto != null ? pkIdDoacaoHasLoteProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmDoacaoHasLoteProduto)) {
            return false;
        }
        FarmDoacaoHasLoteProduto other = (FarmDoacaoHasLoteProduto) object;
        if ((this.pkIdDoacaoHasLoteProduto == null && other.pkIdDoacaoHasLoteProduto != null) || (this.pkIdDoacaoHasLoteProduto != null && !this.pkIdDoacaoHasLoteProduto.equals(other.pkIdDoacaoHasLoteProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmDoacaoHasLoteProduto[ pkIdDoacaoHasLoteProduto=" + pkIdDoacaoHasLoteProduto + " ]";
    }
    
}
