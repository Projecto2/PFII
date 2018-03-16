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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_formador_aux", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiFormadorAux.findAll", query = "SELECT s FROM SupiFormadorAux s"),
    @NamedQuery(name = "SupiFormadorAux.findByPkIdFormadorAux", query = "SELECT s FROM SupiFormadorAux s WHERE s.pkIdFormadorAux = :pkIdFormadorAux")})
public class SupiFormadorAux implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_formador_aux", nullable = false)
    private Integer pkIdFormadorAux;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa", nullable = false)
    @ManyToOne(optional = false)
    private GrlPessoa fkIdPessoa;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_profissao", referencedColumnName = "pk_id_profissao")
    @ManyToOne
    private RhProfissao fkIdProfissao;
    @JoinColumn(name = "fk_id_area_formacao", referencedColumnName = "pk_id_area_formacao")
    @ManyToOne
    private SupiAreaFormacao fkIdAreaFormacao;
    @JoinColumn(name = "fk_id_instituicao_proveniencia", referencedColumnName = "pk_id_instituicao_proveniencia", nullable = false)
    @ManyToOne(optional = false)
    private SupiInstituicaoProveniencia fkIdInstituicaoProveniencia;
    @JoinColumn(name = "fk_id_entidade", referencedColumnName = "pk_id_instituicao_proveniencia")
    @ManyToOne
    private SupiInstituicaoProveniencia fkIdEntidade;
    @JoinColumn(name = "fk_id_tipo_formador", referencedColumnName = "pk_id_tipo_formador", nullable = false)
    @ManyToOne(optional = false)
    private SupiTipoFormador fkIdTipoFormador;
    @OneToMany(mappedBy = "fkIdFormadorAux")
    private List<SupiFormacao> supiFormacaoList;

    public SupiFormadorAux() {
    }

    public SupiFormadorAux(Integer pkIdFormadorAux) {
        this.pkIdFormadorAux = pkIdFormadorAux;
    }

    public Integer getPkIdFormadorAux() {
        return pkIdFormadorAux;
    }

    public void setPkIdFormadorAux(Integer pkIdFormadorAux) {
        this.pkIdFormadorAux = pkIdFormadorAux;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public RhProfissao getFkIdProfissao() {
        return fkIdProfissao;
    }

    public void setFkIdProfissao(RhProfissao fkIdProfissao) {
        this.fkIdProfissao = fkIdProfissao;
    }

    public SupiAreaFormacao getFkIdAreaFormacao() {
        return fkIdAreaFormacao;
    }

    public void setFkIdAreaFormacao(SupiAreaFormacao fkIdAreaFormacao) {
        this.fkIdAreaFormacao = fkIdAreaFormacao;
    }

    public SupiInstituicaoProveniencia getFkIdInstituicaoProveniencia() {
        return fkIdInstituicaoProveniencia;
    }

    public void setFkIdInstituicaoProveniencia(SupiInstituicaoProveniencia fkIdInstituicaoProveniencia) {
        this.fkIdInstituicaoProveniencia = fkIdInstituicaoProveniencia;
    }

    public SupiInstituicaoProveniencia getFkIdEntidade() {
        return fkIdEntidade;
    }

    public void setFkIdEntidade(SupiInstituicaoProveniencia fkIdEntidade) {
        this.fkIdEntidade = fkIdEntidade;
    }

    public SupiTipoFormador getFkIdTipoFormador() {
        return fkIdTipoFormador;
    }

    public void setFkIdTipoFormador(SupiTipoFormador fkIdTipoFormador) {
        this.fkIdTipoFormador = fkIdTipoFormador;
    }

    @XmlTransient
    public List<SupiFormacao> getSupiFormacaoList() {
        return supiFormacaoList;
    }

    public void setSupiFormacaoList(List<SupiFormacao> supiFormacaoList) {
        this.supiFormacaoList = supiFormacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFormadorAux != null ? pkIdFormadorAux.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiFormadorAux)) {
            return false;
        }
        SupiFormadorAux other = (SupiFormadorAux) object;
        if ((this.pkIdFormadorAux == null && other.pkIdFormadorAux != null) || (this.pkIdFormadorAux != null && !this.pkIdFormadorAux.equals(other.pkIdFormadorAux))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiFormadorAux[ pkIdFormadorAux=" + pkIdFormadorAux + " ]";
    }
    
}
