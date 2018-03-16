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
@Table(name = "grl_instituicao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlInstituicao.findAll", query = "SELECT g FROM GrlInstituicao g"),
    @NamedQuery(name = "GrlInstituicao.findByPkIdInstituicao", query = "SELECT g FROM GrlInstituicao g WHERE g.pkIdInstituicao = :pkIdInstituicao"),
    @NamedQuery(name = "GrlInstituicao.findByCodigoInstituicao", query = "SELECT g FROM GrlInstituicao g WHERE g.codigoInstituicao = :codigoInstituicao"),
    @NamedQuery(name = "GrlInstituicao.findByDescricao", query = "SELECT g FROM GrlInstituicao g WHERE g.descricao = :descricao")})
public class GrlInstituicao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_instituicao", nullable = false)
    private Integer pkIdInstituicao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo_instituicao", nullable = false, length = 20)
    private String codigoInstituicao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;
    @OneToMany(mappedBy = "fkIdInstituicaoUltimaDadiva")
    private List<DiagCandidatoDador> diagCandidatoDadorList;
    @JoinColumn(name = "fk_id_contacto", referencedColumnName = "pk_id_contacto", nullable = false)
    @ManyToOne(optional = false)
    private GrlContacto fkIdContacto;
    @JoinColumn(name = "fk_id_endereco", referencedColumnName = "pk_id_endereco", nullable = false)
    @ManyToOne(optional = false)
    private GrlEndereco fkIdEndereco;
    @OneToMany(mappedBy = "fkIdInstituicao")
    private List<AmbTransferencia> ambTransferenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdInstituicao")
    private List<GrlConvenio> grlConvenioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdInstituicao")
    private List<GrlCentroHospitalar> grlCentroHospitalarList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdInstituicao")
    private List<GrlFornecedor> grlFornecedorList;
    @OneToMany(mappedBy = "fkIdInstituicao")
    private List<FarmLocalArmazenamento> farmLocalArmazenamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdInstituicao")
    private List<FarmDoacao> farmDoacaoList;

    public GrlInstituicao() {
    }

    public GrlInstituicao(Integer pkIdInstituicao) {
        this.pkIdInstituicao = pkIdInstituicao;
    }

    public GrlInstituicao(Integer pkIdInstituicao, String codigoInstituicao, String descricao) {
        this.pkIdInstituicao = pkIdInstituicao;
        this.codigoInstituicao = codigoInstituicao;
        this.descricao = descricao;
    }

    public Integer getPkIdInstituicao() {
        return pkIdInstituicao;
    }

    public void setPkIdInstituicao(Integer pkIdInstituicao) {
        this.pkIdInstituicao = pkIdInstituicao;
    }

    public String getCodigoInstituicao() {
        return codigoInstituicao;
    }

    public void setCodigoInstituicao(String codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<DiagCandidatoDador> getDiagCandidatoDadorList() {
        return diagCandidatoDadorList;
    }

    public void setDiagCandidatoDadorList(List<DiagCandidatoDador> diagCandidatoDadorList) {
        this.diagCandidatoDadorList = diagCandidatoDadorList;
    }

    public GrlContacto getFkIdContacto() {
        return fkIdContacto;
    }

    public void setFkIdContacto(GrlContacto fkIdContacto) {
        this.fkIdContacto = fkIdContacto;
    }

    public GrlEndereco getFkIdEndereco() {
        return fkIdEndereco;
    }

    public void setFkIdEndereco(GrlEndereco fkIdEndereco) {
        this.fkIdEndereco = fkIdEndereco;
    }

    @XmlTransient
    public List<AmbTransferencia> getAmbTransferenciaList() {
        return ambTransferenciaList;
    }

    public void setAmbTransferenciaList(List<AmbTransferencia> ambTransferenciaList) {
        this.ambTransferenciaList = ambTransferenciaList;
    }

    @XmlTransient
    public List<GrlConvenio> getGrlConvenioList() {
        return grlConvenioList;
    }

    public void setGrlConvenioList(List<GrlConvenio> grlConvenioList) {
        this.grlConvenioList = grlConvenioList;
    }

    @XmlTransient
    public List<GrlCentroHospitalar> getGrlCentroHospitalarList() {
        return grlCentroHospitalarList;
    }

    public void setGrlCentroHospitalarList(List<GrlCentroHospitalar> grlCentroHospitalarList) {
        this.grlCentroHospitalarList = grlCentroHospitalarList;
    }

    @XmlTransient
    public List<GrlFornecedor> getGrlFornecedorList() {
        return grlFornecedorList;
    }

    public void setGrlFornecedorList(List<GrlFornecedor> grlFornecedorList) {
        this.grlFornecedorList = grlFornecedorList;
    }

    @XmlTransient
    public List<FarmLocalArmazenamento> getFarmLocalArmazenamentoList() {
        return farmLocalArmazenamentoList;
    }

    public void setFarmLocalArmazenamentoList(List<FarmLocalArmazenamento> farmLocalArmazenamentoList) {
        this.farmLocalArmazenamentoList = farmLocalArmazenamentoList;
    }

    @XmlTransient
    public List<FarmDoacao> getFarmDoacaoList() {
        return farmDoacaoList;
    }

    public void setFarmDoacaoList(List<FarmDoacao> farmDoacaoList) {
        this.farmDoacaoList = farmDoacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdInstituicao != null ? pkIdInstituicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlInstituicao)) {
            return false;
        }
        GrlInstituicao other = (GrlInstituicao) object;
        if ((this.pkIdInstituicao == null && other.pkIdInstituicao != null) || (this.pkIdInstituicao != null && !this.pkIdInstituicao.equals(other.pkIdInstituicao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlInstituicao[ pkIdInstituicao=" + pkIdInstituicao + " ]";
    }
    
}
