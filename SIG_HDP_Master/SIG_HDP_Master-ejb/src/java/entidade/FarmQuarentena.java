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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_quarentena", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmQuarentena.findAll", query = "SELECT f FROM FarmQuarentena f"),
    @NamedQuery(name = "FarmQuarentena.findByPkIdQuarentena", query = "SELECT f FROM FarmQuarentena f WHERE f.pkIdQuarentena = :pkIdQuarentena"),
    @NamedQuery(name = "FarmQuarentena.findByQuantidade", query = "SELECT f FROM FarmQuarentena f WHERE f.quantidade = :quantidade"),
    @NamedQuery(name = "FarmQuarentena.findByMotivo", query = "SELECT f FROM FarmQuarentena f WHERE f.motivo = :motivo"),
    @NamedQuery(name = "FarmQuarentena.findByDataCadastro", query = "SELECT f FROM FarmQuarentena f WHERE f.dataCadastro = :dataCadastro")})
public class FarmQuarentena implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_quarentena", nullable = false)
    private Integer pkIdQuarentena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "motivo", nullable = false, length = 200)
    private String motivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_local_origem", referencedColumnName = "pk_id_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmLocalArmazenamento fkIdLocalOrigem;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmLoteProduto fkIdLoteProduto;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public FarmQuarentena() {
    }

    public FarmQuarentena(Integer pkIdQuarentena) {
        this.pkIdQuarentena = pkIdQuarentena;
    }

    public FarmQuarentena(Integer pkIdQuarentena, int quantidade, String motivo, Date dataCadastro) {
        this.pkIdQuarentena = pkIdQuarentena;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdQuarentena() {
        return pkIdQuarentena;
    }

    public void setPkIdQuarentena(Integer pkIdQuarentena) {
        this.pkIdQuarentena = pkIdQuarentena;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public FarmLocalArmazenamento getFkIdLocalOrigem() {
        return fkIdLocalOrigem;
    }

    public void setFkIdLocalOrigem(FarmLocalArmazenamento fkIdLocalOrigem) {
        this.fkIdLocalOrigem = fkIdLocalOrigem;
    }

    public FarmLoteProduto getFkIdLoteProduto() {
        return fkIdLoteProduto;
    }

    public void setFkIdLoteProduto(FarmLoteProduto fkIdLoteProduto) {
        this.fkIdLoteProduto = fkIdLoteProduto;
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
        hash += (pkIdQuarentena != null ? pkIdQuarentena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmQuarentena)) {
            return false;
        }
        FarmQuarentena other = (FarmQuarentena) object;
        if ((this.pkIdQuarentena == null && other.pkIdQuarentena != null) || (this.pkIdQuarentena != null && !this.pkIdQuarentena.equals(other.pkIdQuarentena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmQuarentena[ pkIdQuarentena=" + pkIdQuarentena + " ]";
    }
    
}
