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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_cid_configuracoes", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidConfiguracoes.findAll", query = "SELECT a FROM AmbCidConfiguracoes a"),
    @NamedQuery(name = "AmbCidConfiguracoes.findByPkIdConfiguracoes", query = "SELECT a FROM AmbCidConfiguracoes a WHERE a.pkIdConfiguracoes = :pkIdConfiguracoes"),
    @NamedQuery(name = "AmbCidConfiguracoes.findByIdEspecialidade", query = "SELECT a FROM AmbCidConfiguracoes a WHERE a.idEspecialidade = :idEspecialidade"),
    @NamedQuery(name = "AmbCidConfiguracoes.findByIdConta", query = "SELECT a FROM AmbCidConfiguracoes a WHERE a.idConta = :idConta"),
    @NamedQuery(name = "AmbCidConfiguracoes.findByIdNomePerfil", query = "SELECT a FROM AmbCidConfiguracoes a WHERE a.idNomePerfil = :idNomePerfil"),
    @NamedQuery(name = "AmbCidConfiguracoes.findByIdDoencasPrioridades", query = "SELECT a FROM AmbCidConfiguracoes a WHERE a.idDoencasPrioridades = :idDoencasPrioridades")})
public class AmbCidConfiguracoes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_configuracoes", nullable = false)
    private Integer pkIdConfiguracoes;
    @Column(name = "id_especialidade")
    private Integer idEspecialidade;
    @Column(name = "id_conta")
    private Integer idConta;
    @Size(max = 60)
    @Column(name = "id_nome_perfil", length = 60)
    private String idNomePerfil;
    @Column(name = "id_doencas_prioridades")
    private Integer idDoencasPrioridades;

    public AmbCidConfiguracoes() {
    }

    public AmbCidConfiguracoes(Integer pkIdConfiguracoes) {
        this.pkIdConfiguracoes = pkIdConfiguracoes;
    }

    public Integer getPkIdConfiguracoes() {
        return pkIdConfiguracoes;
    }

    public void setPkIdConfiguracoes(Integer pkIdConfiguracoes) {
        this.pkIdConfiguracoes = pkIdConfiguracoes;
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public String getIdNomePerfil() {
        return idNomePerfil;
    }

    public void setIdNomePerfil(String idNomePerfil) {
        this.idNomePerfil = idNomePerfil;
    }

    public Integer getIdDoencasPrioridades() {
        return idDoencasPrioridades;
    }

    public void setIdDoencasPrioridades(Integer idDoencasPrioridades) {
        this.idDoencasPrioridades = idDoencasPrioridades;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConfiguracoes != null ? pkIdConfiguracoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidConfiguracoes)) {
            return false;
        }
        AmbCidConfiguracoes other = (AmbCidConfiguracoes) object;
        if ((this.pkIdConfiguracoes == null && other.pkIdConfiguracoes != null) || (this.pkIdConfiguracoes != null && !this.pkIdConfiguracoes.equals(other.pkIdConfiguracoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidConfiguracoes[ pkIdConfiguracoes=" + pkIdConfiguracoes + " ]";
    }
    
}
