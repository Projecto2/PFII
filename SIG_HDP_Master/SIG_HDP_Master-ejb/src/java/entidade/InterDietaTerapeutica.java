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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_dieta_terapeutica", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterDietaTerapeutica.findAll", query = "SELECT i FROM InterDietaTerapeutica i"),
    @NamedQuery(name = "InterDietaTerapeutica.findByPkIdDietaTerapeutica", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.pkIdDietaTerapeutica = :pkIdDietaTerapeutica"),
    @NamedQuery(name = "InterDietaTerapeutica.findByFase", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.fase = :fase"),
    @NamedQuery(name = "InterDietaTerapeutica.findByAlimento", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.alimento = :alimento"),
    @NamedQuery(name = "InterDietaTerapeutica.findByMlRefeicao", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.mlRefeicao = :mlRefeicao"),
    @NamedQuery(name = "InterDietaTerapeutica.findByNRefeicaoDia", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.nRefeicaoDia = :nRefeicaoDia"),
    @NamedQuery(name = "InterDietaTerapeutica.findByMlDia", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.mlDia = :mlDia"),
    @NamedQuery(name = "InterDietaTerapeutica.findByFerro", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.ferro = :ferro"),
    @NamedQuery(name = "InterDietaTerapeutica.findByQuantidadeTomada", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.quantidadeTomada = :quantidadeTomada"),
    @NamedQuery(name = "InterDietaTerapeutica.findByPapa", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.papa = :papa"),
    @NamedQuery(name = "InterDietaTerapeutica.findByOutros", query = "SELECT i FROM InterDietaTerapeutica i WHERE i.outros = :outros")})
public class InterDietaTerapeutica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_dieta_terapeutica", nullable = false)
    private Long pkIdDietaTerapeutica;
    @Size(max = 45)
    @Column(name = "fase", length = 45)
    private String fase;
    @Column(name = "alimento")
    private Integer alimento;
    @Size(max = 45)
    @Column(name = "ml_refeicao", length = 45)
    private String mlRefeicao;
    @Column(name = "n_refeicao_dia")
    private Integer nRefeicaoDia;
    @Size(max = 45)
    @Column(name = "ml_dia", length = 45)
    private String mlDia;
    @Size(max = 45)
    @Column(name = "ferro", length = 45)
    private String ferro;
    @Size(max = 45)
    @Column(name = "quantidade_tomada", length = 45)
    private String quantidadeTomada;
    @Size(max = 45)
    @Column(name = "papa", length = 45)
    private String papa;
    @Size(max = 45)
    @Column(name = "outros", length = 45)
    private String outros;
    @JoinColumn(name = "fk_id_tratamento_malnutricao", referencedColumnName = "pk_id_tratamento_malnutricao", nullable = false)
    @ManyToOne(optional = false)
    private InterTratamentoMalnutricao fkIdTratamentoMalnutricao;

    public InterDietaTerapeutica() {
    }

    public InterDietaTerapeutica(Long pkIdDietaTerapeutica) {
        this.pkIdDietaTerapeutica = pkIdDietaTerapeutica;
    }

    public Long getPkIdDietaTerapeutica() {
        return pkIdDietaTerapeutica;
    }

    public void setPkIdDietaTerapeutica(Long pkIdDietaTerapeutica) {
        this.pkIdDietaTerapeutica = pkIdDietaTerapeutica;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Integer getAlimento() {
        return alimento;
    }

    public void setAlimento(Integer alimento) {
        this.alimento = alimento;
    }

    public String getMlRefeicao() {
        return mlRefeicao;
    }

    public void setMlRefeicao(String mlRefeicao) {
        this.mlRefeicao = mlRefeicao;
    }

    public Integer getNRefeicaoDia() {
        return nRefeicaoDia;
    }

    public void setNRefeicaoDia(Integer nRefeicaoDia) {
        this.nRefeicaoDia = nRefeicaoDia;
    }

    public String getMlDia() {
        return mlDia;
    }

    public void setMlDia(String mlDia) {
        this.mlDia = mlDia;
    }

    public String getFerro() {
        return ferro;
    }

    public void setFerro(String ferro) {
        this.ferro = ferro;
    }

    public String getQuantidadeTomada() {
        return quantidadeTomada;
    }

    public void setQuantidadeTomada(String quantidadeTomada) {
        this.quantidadeTomada = quantidadeTomada;
    }

    public String getPapa() {
        return papa;
    }

    public void setPapa(String papa) {
        this.papa = papa;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    public InterTratamentoMalnutricao getFkIdTratamentoMalnutricao() {
        return fkIdTratamentoMalnutricao;
    }

    public void setFkIdTratamentoMalnutricao(InterTratamentoMalnutricao fkIdTratamentoMalnutricao) {
        this.fkIdTratamentoMalnutricao = fkIdTratamentoMalnutricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDietaTerapeutica != null ? pkIdDietaTerapeutica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterDietaTerapeutica)) {
            return false;
        }
        InterDietaTerapeutica other = (InterDietaTerapeutica) object;
        if ((this.pkIdDietaTerapeutica == null && other.pkIdDietaTerapeutica != null) || (this.pkIdDietaTerapeutica != null && !this.pkIdDietaTerapeutica.equals(other.pkIdDietaTerapeutica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterDietaTerapeutica[ pkIdDietaTerapeutica=" + pkIdDietaTerapeutica + " ]";
    }
    
}
