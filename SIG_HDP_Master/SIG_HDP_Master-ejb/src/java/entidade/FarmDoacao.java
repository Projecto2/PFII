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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_doacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmDoacao.findAll", query = "SELECT f FROM FarmDoacao f"),
    @NamedQuery(name = "FarmDoacao.findByPkIdDoacao", query = "SELECT f FROM FarmDoacao f WHERE f.pkIdDoacao = :pkIdDoacao"),
    @NamedQuery(name = "FarmDoacao.findByDataCadastro", query = "SELECT f FROM FarmDoacao f WHERE f.dataCadastro = :dataCadastro")})
public class FarmDoacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_doacao", nullable = false)
    private Integer pkIdDoacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdDoacao")
    private List<FarmDoacaoHasLoteProduto> farmDoacaoHasLoteProdutoList;
    @JoinColumn(name = "fk_id_local_armazenamento", referencedColumnName = "pk_id_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmLocalArmazenamento fkIdLocalArmazenamento;
    @JoinColumn(name = "fk_id_instituicao", referencedColumnName = "pk_id_instituicao", nullable = false)
    @ManyToOne(optional = false)
    private GrlInstituicao fkIdInstituicao;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public FarmDoacao() {
    }

    public FarmDoacao(Integer pkIdDoacao) {
        this.pkIdDoacao = pkIdDoacao;
    }

    public FarmDoacao(Integer pkIdDoacao, Date dataCadastro) {
        this.pkIdDoacao = pkIdDoacao;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdDoacao() {
        return pkIdDoacao;
    }

    public void setPkIdDoacao(Integer pkIdDoacao) {
        this.pkIdDoacao = pkIdDoacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @XmlTransient
    public List<FarmDoacaoHasLoteProduto> getFarmDoacaoHasLoteProdutoList() {
        return farmDoacaoHasLoteProdutoList;
    }

    public void setFarmDoacaoHasLoteProdutoList(List<FarmDoacaoHasLoteProduto> farmDoacaoHasLoteProdutoList) {
        this.farmDoacaoHasLoteProdutoList = farmDoacaoHasLoteProdutoList;
    }

    public FarmLocalArmazenamento getFkIdLocalArmazenamento() {
        return fkIdLocalArmazenamento;
    }

    public void setFkIdLocalArmazenamento(FarmLocalArmazenamento fkIdLocalArmazenamento) {
        this.fkIdLocalArmazenamento = fkIdLocalArmazenamento;
    }

    public GrlInstituicao getFkIdInstituicao() {
        return fkIdInstituicao;
    }

    public void setFkIdInstituicao(GrlInstituicao fkIdInstituicao) {
        this.fkIdInstituicao = fkIdInstituicao;
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
        hash += (pkIdDoacao != null ? pkIdDoacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmDoacao)) {
            return false;
        }
        FarmDoacao other = (FarmDoacao) object;
        if ((this.pkIdDoacao == null && other.pkIdDoacao != null) || (this.pkIdDoacao != null && !this.pkIdDoacao.equals(other.pkIdDoacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmDoacao[ pkIdDoacao=" + pkIdDoacao + " ]";
    }
    
}
