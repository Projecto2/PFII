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
@Table(name = "inter_opcao_medicacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterOpcaoMedicacao.findAll", query = "SELECT i FROM InterOpcaoMedicacao i"),
    @NamedQuery(name = "InterOpcaoMedicacao.findByDescricao", query = "SELECT i FROM InterOpcaoMedicacao i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterOpcaoMedicacao.findByCodigo", query = "SELECT i FROM InterOpcaoMedicacao i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "InterOpcaoMedicacao.findByPkIdOpcaoMedicacao", query = "SELECT i FROM InterOpcaoMedicacao i WHERE i.pkIdOpcaoMedicacao = :pkIdOpcaoMedicacao")})
public class InterOpcaoMedicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @Size(max = 45)
    @Column(name = "codigo", length = 45)
    private String codigo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_opcao_medicacao", nullable = false)
    private Integer pkIdOpcaoMedicacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdOpcaoMedicacao")
    private List<InterRealizarMedicacao> interRealizarMedicacaoList;

    public InterOpcaoMedicacao() {
    }

    public InterOpcaoMedicacao(Integer pkIdOpcaoMedicacao) {
        this.pkIdOpcaoMedicacao = pkIdOpcaoMedicacao;
    }

    public InterOpcaoMedicacao(Integer pkIdOpcaoMedicacao, String descricao) {
        this.pkIdOpcaoMedicacao = pkIdOpcaoMedicacao;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getPkIdOpcaoMedicacao() {
        return pkIdOpcaoMedicacao;
    }

    public void setPkIdOpcaoMedicacao(Integer pkIdOpcaoMedicacao) {
        this.pkIdOpcaoMedicacao = pkIdOpcaoMedicacao;
    }

    @XmlTransient
    public List<InterRealizarMedicacao> getInterRealizarMedicacaoList() {
        return interRealizarMedicacaoList;
    }

    public void setInterRealizarMedicacaoList(List<InterRealizarMedicacao> interRealizarMedicacaoList) {
        this.interRealizarMedicacaoList = interRealizarMedicacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdOpcaoMedicacao != null ? pkIdOpcaoMedicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterOpcaoMedicacao)) {
            return false;
        }
        InterOpcaoMedicacao other = (InterOpcaoMedicacao) object;
        if ((this.pkIdOpcaoMedicacao == null && other.pkIdOpcaoMedicacao != null) || (this.pkIdOpcaoMedicacao != null && !this.pkIdOpcaoMedicacao.equals(other.pkIdOpcaoMedicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterOpcaoMedicacao[ pkIdOpcaoMedicacao=" + pkIdOpcaoMedicacao + " ]";
    }
    
}
