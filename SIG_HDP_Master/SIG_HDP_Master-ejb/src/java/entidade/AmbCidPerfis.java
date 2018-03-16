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
@Table(name = "amb_cid_perfis", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidPerfis.findAll", query = "SELECT a FROM AmbCidPerfis a"),
    @NamedQuery(name = "AmbCidPerfis.findByPkIdNome", query = "SELECT a FROM AmbCidPerfis a WHERE a.pkIdNome = :pkIdNome"),
    @NamedQuery(name = "AmbCidPerfis.findByProfundidade", query = "SELECT a FROM AmbCidPerfis a WHERE a.profundidade = :profundidade")})
public class AmbCidPerfis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "pk_id_nome", nullable = false, length = 60)
    private String pkIdNome;
    @Column(name = "profundidade")
    private Integer profundidade;
    @JoinColumn(name = "fk_id_tipo", referencedColumnName = "pk_id_tipos")
    @ManyToOne
    private AmbCidPerfilTipos fkIdTipo;
    @OneToMany(mappedBy = "fkIdPerfilPai")
    private List<AmbCidPerfis> ambCidPerfisList;
    @JoinColumn(name = "fk_id_perfil_pai", referencedColumnName = "pk_id_nome")
    @ManyToOne
    private AmbCidPerfis fkIdPerfilPai;
    @JoinColumn(name = "fk_id_especialidades", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidades;
    @JoinColumn(name = "fk_id_dono", referencedColumnName = "pk_id_conta")
    @ManyToOne
    private SegConta fkIdDono;
    @OneToMany(mappedBy = "fkIdPerfil")
    private List<AmbCidPerfisDoencas> ambCidPerfisDoencasList;

    public AmbCidPerfis() {
    }

    public AmbCidPerfis(String pkIdNome) {
        this.pkIdNome = pkIdNome;
    }

    public String getPkIdNome() {
        return pkIdNome;
    }

    public void setPkIdNome(String pkIdNome) {
        this.pkIdNome = pkIdNome;
    }

    public Integer getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(Integer profundidade) {
        this.profundidade = profundidade;
    }

    public AmbCidPerfilTipos getFkIdTipo() {
        return fkIdTipo;
    }

    public void setFkIdTipo(AmbCidPerfilTipos fkIdTipo) {
        this.fkIdTipo = fkIdTipo;
    }

    @XmlTransient
    public List<AmbCidPerfis> getAmbCidPerfisList() {
        return ambCidPerfisList;
    }

    public void setAmbCidPerfisList(List<AmbCidPerfis> ambCidPerfisList) {
        this.ambCidPerfisList = ambCidPerfisList;
    }

    public AmbCidPerfis getFkIdPerfilPai() {
        return fkIdPerfilPai;
    }

    public void setFkIdPerfilPai(AmbCidPerfis fkIdPerfilPai) {
        this.fkIdPerfilPai = fkIdPerfilPai;
    }

    public GrlEspecialidade getFkIdEspecialidades() {
        return fkIdEspecialidades;
    }

    public void setFkIdEspecialidades(GrlEspecialidade fkIdEspecialidades) {
        this.fkIdEspecialidades = fkIdEspecialidades;
    }

    public SegConta getFkIdDono() {
        return fkIdDono;
    }

    public void setFkIdDono(SegConta fkIdDono) {
        this.fkIdDono = fkIdDono;
    }

    @XmlTransient
    public List<AmbCidPerfisDoencas> getAmbCidPerfisDoencasList() {
        return ambCidPerfisDoencasList;
    }

    public void setAmbCidPerfisDoencasList(List<AmbCidPerfisDoencas> ambCidPerfisDoencasList) {
        this.ambCidPerfisDoencasList = ambCidPerfisDoencasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdNome != null ? pkIdNome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidPerfis)) {
            return false;
        }
        AmbCidPerfis other = (AmbCidPerfis) object;
        if ((this.pkIdNome == null && other.pkIdNome != null) || (this.pkIdNome != null && !this.pkIdNome.equals(other.pkIdNome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidPerfis[ pkIdNome=" + pkIdNome + " ]";
    }
    
}
