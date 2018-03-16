/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidade.GrlCentroHospitalar;
import entidade.GrlComuna;
import entidade.GrlDistrito;
import entidade.GrlEspecialidade;
import entidade.GrlEstadoCivil;
import entidade.GrlInstituicao;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlProvincia;
import entidade.GrlReligiao;
import entidade.GrlSexo;
import entidade.GrlTipoDocumentoIdentificacao;
import entidade.RhEscolaUniversidade;
import entidade.RhCategoriaProfissional;
import entidade.RhDepartamento;
import entidade.RhEstadoCandidato;
import entidade.RhEstadoContrato;
import entidade.RhEstadoEstagiario;
import entidade.RhEstadoFerias;
import entidade.RhEstadoFuncionario;
import entidade.RhFormaPagamento;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.RhSubsidio;
import entidade.RhTipoContrato;
import entidade.RhTipoEstagio;
import entidade.RhTipoFalta;
import entidade.RhTipoFuncionario;
import entidade.RhCurso;
import entidade.RhEspecialidadeCurso;
import entidade.RhFuncao;
import entidade.RhPeriodoAulas;
import entidade.RhTipoDeHorarioTrabalho;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlComunaFacade;
import sessao.GrlDistritoFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.GrlEstadoCivilFacade;
import sessao.GrlInstituicaoFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlPaisFacade;
import sessao.GrlProvinciaFacade;
import sessao.GrlReligiaoFacade;
import sessao.GrlSexoFacade;
import sessao.GrlTipoDocumentoIdentificacaoFacade;
import sessao.RhEscolaUniversidadeFacade;
import sessao.RhCategoriaProfissionalFacade;
import sessao.RhDepartamentoFacade;
import sessao.RhEstadoCandidatoFacade;
import sessao.RhEstadoContratoFacade;
import sessao.RhEstadoEstagiarioFacade;
import sessao.RhEstadoFeriasFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFormaPagamentoFacade;
import sessao.RhNivelAcademicoFacade;
import sessao.RhProfissaoFacade;
import sessao.RhSeccaoTrabalhoFacade;
import sessao.RhSubsidioFacade;
import sessao.RhTipoContratoFacade;
import sessao.RhTipoEstagioFacade;
import sessao.RhTipoFaltaFacade;
import sessao.RhTipoFuncionarioFacade;
import sessao.RhCursoFacade;
import sessao.RhEspecialidadeCursoFacade;
import sessao.RhFuncaoFacade;
import sessao.RhPeriodoAulasFacade;
import sessao.RhTipoDeHorarioTrabalhoFacade;

/**
 *
 * @author Ornela F. Boaventura
 * @coauthor(a) Elizangela Gaspar
 * @coauthor(a) Garcia Paulo
 */
@ManagedBean
@SessionScoped
public class ItensAjaxBean implements Serializable
{

    @EJB
    private GrlTipoDocumentoIdentificacaoFacade tipoDocumentoIdentificacaoFacade;
    @EJB
    private GrlSexoFacade sexoFacade;
    @EJB
    private GrlReligiaoFacade religiaoFacade;
    @EJB
    private GrlPaisFacade paisFacade;
    @EJB
    private GrlProvinciaFacade provinciaFacade;
    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlComunaFacade comunaFacade;
    @EJB
    private GrlDistritoFacade grlDistritoFacade;
    @EJB
    private GrlEstadoCivilFacade estadoCivilFacade;
    @EJB
    private GrlInstituicaoFacade instituicaoFacade;
    @EJB
    private RhNivelAcademicoFacade nivelAcademicoFacade;
    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;
    @EJB
    private RhEscolaUniversidadeFacade instituicaoRhFacade;
    @EJB
    private RhTipoFuncionarioFacade tipoFuncionarioFacade;
    @EJB
    private RhTipoEstagioFacade tipoEstagioFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    private RhEstadoCandidatoFacade estadoCandidatoFacade;
    @EJB
    private RhEstadoEstagiarioFacade estadoEstagiarioFacade;
    @EJB
    private RhEstadoContratoFacade estadoContratoFacade;
    @EJB
    private RhEstadoFeriasFacade estadoFeriasFacade;
    @EJB
    private RhDepartamentoFacade departamentoFacade;
    @EJB
    private RhSeccaoTrabalhoFacade seccaoTrabalhoFacade;
    @EJB
    private RhProfissaoFacade profissaoFacade;
    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;
    @EJB
    private GrlEspecialidadeFacade especialidadeFacade;
    @EJB
    private RhTipoFaltaFacade tipoFaltaFacade;
    @EJB
    private RhFuncaoFacade funcaoFacade;
    @EJB
    private RhTipoContratoFacade tipoContratoFacade;
    @EJB
    private RhFormaPagamentoFacade formaPagamentoFacade;
    @EJB
    private RhSubsidioFacade subsidioFacade;
    @EJB
    private RhCursoFacade cursoFacade;
    @EJB
    private RhEspecialidadeCursoFacade especialidadeCursoFacade;
    @EJB
    private RhPeriodoAulasFacade periodoAulasFacade;
    @EJB
    private RhTipoDeHorarioTrabalhoFacade TipoDeHorarioTrabalhoFacade;

    private List<GrlTipoDocumentoIdentificacao> tipoDocumentoIdentificacaoList;
    private List<GrlSexo> sexoList;
    private List<GrlReligiao> religiaoList;
    private List<GrlPais> paisList;
    private List<GrlProvincia> provinciaList;
    private List<GrlMunicipio> municipioList;
    private List<GrlComuna> comunaList;
    private List<GrlDistrito> distritoList;
    private List<GrlEstadoCivil> estadoCivilList;
    private List<GrlInstituicao> instituicaoList;
    private List<RhNivelAcademico> nivelAcademicoList;
    private List<RhEscolaUniversidade> instituicaoRhList;
    private List<RhTipoFuncionario> tipoFuncionarioList;
    private List<RhTipoEstagio> tipoEstagioList;
    private List<RhEstadoFuncionario> estadoFuncionarioList;
    private List<RhEstadoCandidato> estadoCandidatoList;
    private List<RhEstadoEstagiario> estadoEstagiarioList;
    private List<RhEstadoContrato> estadoContratoList;
    private List<RhEstadoFerias> estadoFeriasList;
    private List<GrlCentroHospitalar> centroHospitalarList;
    private List<RhDepartamento> departamentoList;
    private List<RhSeccaoTrabalho> seccaoTrabalhoList;
    private List<RhProfissao> profissaoList;
    private List<RhCategoriaProfissional> categoriaProfissionalList;
    private List<GrlEspecialidade> especialidadeList;
    private List<RhTipoFalta> tipoFaltaList;
    private List<RhFuncao> funcaoList;
    private List<RhTipoContrato> tipoContratoList;
    private List<RhFormaPagamento> formaPagamentoList;
    private List<RhSubsidio> subsidioList;
    private List<RhCurso> cursoList;
    private List<RhEspecialidadeCurso> especialidadeCursoList;
    private List<RhPeriodoAulas> periodoAulasList;
    private List<RhTipoDeHorarioTrabalho> tipoDeHorarioTrabalhoList;

    /**
     * Creates a new instance of ItensSelectGerais
     */
    public ItensAjaxBean ()
    {
    }

    public static ItensAjaxBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }


    public void changeCentroHospitalar (ValueChangeEvent e)
    {
        centroHospitalar = (Integer) e.getNewValue();
    }

    public void changePais (ValueChangeEvent e)
    {
        pais = (Integer) e.getNewValue();
        provincia = municipio = null;
        actualizarProvincias(pais);
    }

    public void changeProvincia (ValueChangeEvent e)
    {
        provincia = (Integer) e.getNewValue();
        municipio = null;
        actualizarMunicipios(provincia);
    }

    public void changeMunicipio (ValueChangeEvent e)
    {
        municipio = (Integer) e.getNewValue();
        actualizarComunas(municipio);
        actualizarDistritos(municipio);
    }

    public void changeDepartamento (ValueChangeEvent e)
    {
        departamento = (Integer) e.getNewValue();
        actualizarSeccoesTrabalho(departamento);
    }

    public void changeProfissao (ValueChangeEvent e)
    {
        profissao = Integer.parseInt(e.getNewValue().toString());
        actualizarEspecialidades(Integer.parseInt(e.getNewValue().toString()));
    }

    public void changeCurso (ValueChangeEvent e)
    {
        curso = (Integer) e.getNewValue();
        actualizarEspecialidadesCurso(curso);
    }

    private void actualizarProvincias (Integer idPais)
    {
        provinciaList = provinciaFacade.pesquisaPorPais(idPais);
    }

    private void actualizarMunicipios (Integer idProvincia)
    {
        municipioList = municipioFacade.pesquisaPorProvincia(idProvincia);
    }

    private void actualizarComunas (Integer idMunicipio)
    {
        comunaList = comunaFacade.pesquisaPorMunicipio(idMunicipio);
    }

    private void actualizarDistritos (Integer idMunicipio)
    {
        distritoList = grlDistritoFacade.pesquisaPorMunicipio(idMunicipio);
    }

    private void actualizarSeccoesTrabalho (Integer idDepartamento)
    {
        seccaoTrabalhoList = null;
        seccaoTrabalhoList = seccaoTrabalhoFacade.pesquisaPorDepartamento(idDepartamento);
    }

    public void actualizarEspecialidades (Integer idProfissao)
    {
        especialidadeList = null;
        especialidadeList = especialidadeFacade.pesquisaPorProfissao(idProfissao);
    }

    private void actualizarEspecialidadesCurso (Integer idProfissao)
    {
        especialidadeCursoList = null;
        especialidadeCursoList = especialidadeCursoFacade.pesquisaPorCurso(idProfissao);
    }

    public List<GrlTipoDocumentoIdentificacao> getTipoDocumentoIdentificacaoList ()
    {
        tipoDocumentoIdentificacaoList = tipoDocumentoIdentificacaoFacade.findAll();
        return tipoDocumentoIdentificacaoList;
    }

    public List<GrlSexo> getSexoList ()
    {
        sexoList = sexoFacade.findAll();
        return sexoList;
    }

    public List<GrlReligiao> getReligiaoList ()
    {
        religiaoList = religiaoFacade.findAll();
        return religiaoList;
    }

    public List<GrlPais> getPaisList ()
    {
        paisList = paisFacade.findAll();

        actualizarProvincias(null);
        if (!paisList.isEmpty())
        {
            if (pais == null)
            {
                pais = paisList.get(0).getPkIdPais();
            }
            actualizarProvincias(pais);
        }
        return paisList;
    }

    public List<GrlProvincia> getProvinciaList ()
    {
        actualizarMunicipios(null);
        if (provinciaList != null)
        {
            if (!provinciaList.isEmpty())
            {
                if (provincia == null)
                {
                    provincia = provinciaList.get(0).getPkIdProvincia();
                }
                actualizarMunicipios(provincia);
            }
        }
        return provinciaList;
    }

    public List<GrlMunicipio> getMunicipioList ()
    {
        actualizarComunas(null);
        actualizarDistritos(null);
        if (municipioList != null)
        {
            if (!municipioList.isEmpty())
            {
                if (municipio == null)
                {
                    municipio = municipioList.get(0).getPkIdMunicipio();
                }
                actualizarComunas(municipio);
                actualizarDistritos(municipio);
            }
        }
        return municipioList;
    }

    public List<GrlComuna> getComunaList ()
    {
        return comunaList;
    }

    public List<GrlDistrito> getDistritoList ()
    {
        return distritoList;
    }

    public List<GrlEstadoCivil> getEstadoCivilList ()
    {
        estadoCivilList = estadoCivilFacade.findAll();
        return estadoCivilList;
    }

    public List<GrlInstituicao> getInstituicaoList ()
    {
        instituicaoList = instituicaoFacade.findAll();
        return instituicaoList;
    }

    public List<RhNivelAcademico> getNivelAcademicoList ()
    {
        nivelAcademicoList = nivelAcademicoFacade.findAll();
        return nivelAcademicoList;
    }

    public List<RhEscolaUniversidade> getInstituicaoRhList ()
    {
        instituicaoRhList = instituicaoRhFacade.findAll();
        return instituicaoRhList;
    }

    public List<RhTipoFuncionario> getTipoFuncionarioList ()
    {
        tipoFuncionarioList = tipoFuncionarioFacade.findAll();
        return tipoFuncionarioList;
    }

    public List<RhTipoEstagio> getTipoEstagioList ()
    {
        tipoEstagioList = tipoEstagioFacade.findAll();
        return tipoEstagioList;
    }

    public List<RhEstadoFuncionario> getEstadoFuncionarioList ()
    {
        estadoFuncionarioList = estadoFuncionarioFacade.findAll();
        return estadoFuncionarioList;
    }

    public List<RhEstadoCandidato> getEstadoCandidatoList ()
    {
        estadoCandidatoList = estadoCandidatoFacade.findAll();
        return estadoCandidatoList;
    }

    public List<RhEstadoEstagiario> getEstadoEstagiarioList ()
    {
        estadoEstagiarioList = estadoEstagiarioFacade.findAll();
        return estadoEstagiarioList;
    }

    public List<RhEstadoContrato> getEstadoContratoList ()
    {
        estadoContratoList = estadoContratoFacade.findAll();
        return estadoContratoList;
    }

    public List<RhEstadoFerias> getEstadoFeriasList ()
    {
        estadoFeriasList = estadoFeriasFacade.findAll();
        return estadoFeriasList;
    }

    public List<GrlCentroHospitalar> getCentroHospitalarList ()
    {
        centroHospitalarList = centroHospitalarFacade.findAll();
        return centroHospitalarList;
    }

    public List<RhDepartamento> getDepartamentoList ()
    {
        departamentoList = departamentoFacade.findAll();

        actualizarSeccoesTrabalho(null);
        if (!departamentoList.isEmpty())
        {
            if (departamento == null)
            {
                departamento = departamentoList.get(0).getPkIdDepartamento();
            }
            actualizarSeccoesTrabalho(departamento);
        }

        return departamentoList;
    }

    public List<RhSeccaoTrabalho> getSeccaoTrabalhoList ()
    {
        return seccaoTrabalhoList;
    }

    public List<RhProfissao> getProfissaoList ()
    {
        profissaoList = profissaoFacade.findAll();

        actualizarEspecialidades(null);

        if (!profissaoList.isEmpty())
        {
            if (profissao == null)
            {
                profissao = profissaoList.get(0).getPkIdProfissao();
            }
            actualizarEspecialidades(profissao);
        }
        return profissaoList;
    }

    public List<RhCategoriaProfissional> getCategoriaProfissionalList ()
    {
        categoriaProfissionalList = categoriaProfissionalFacade.findAll();
        return categoriaProfissionalList;
    }

    public List<GrlEspecialidade> getEspecialidadeList ()
    {
        return especialidadeList;
    }

    public List<RhTipoFalta> getTipoFaltaList ()
    {
        tipoFaltaList = tipoFaltaFacade.findAll();
        return tipoFaltaList;
    }

    public List<RhFuncao> getFuncaoList ()
    {
        funcaoList = funcaoFacade.findAll();
        return funcaoList;
    }

    public List<RhTipoDeHorarioTrabalho> getTipoDeHorarioTrabalhoList ()
    {
        tipoDeHorarioTrabalhoList = TipoDeHorarioTrabalhoFacade.findAll();
        return tipoDeHorarioTrabalhoList;
    }

    public List<RhTipoContrato> getTipoContratoList ()
    {
        tipoContratoList = tipoContratoFacade.findAll();
        return tipoContratoList;
    }

    public List<RhFormaPagamento> getFormaPagamentoList ()
    {
        formaPagamentoList = formaPagamentoFacade.findAll();
        return formaPagamentoList;
    }

    public List<RhSubsidio> getSubsidioList ()
    {
        subsidioList = subsidioFacade.findAll();
        return subsidioList;
    }

    public List<RhCurso> getCursoList ()
    {
        cursoList = cursoFacade.findAll();

        actualizarEspecialidadesCurso(null);

        if (!cursoList.isEmpty())
        {
            if (curso == null)
            {
                curso = cursoList.get(0).getPkIdCurso();
            }
            actualizarEspecialidadesCurso(curso);
        }
        return cursoList;
    }

    public List<RhEspecialidadeCurso> getEspecialidadeCursoList ()
    {
        return especialidadeCursoList;
    }

    public List<RhPeriodoAulas> getPeriodoAulasList ()
    {
        periodoAulasList = periodoAulasFacade.findAll();
        return periodoAulasList;
    }

    public boolean renderCentroDepartamentoSeccaoFuncionario ()
    {
        for (GrlCentroHospitalar centro : centroHospitalarFacade.findAll())
        {
            if (centro.getPkIdCentro() == centroHospitalar)
            {
                if (centro.getFkIdInstituicao().getCodigoInstituicao().equalsIgnoreCase(Constantes.HDP))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * Atributos para pegar valores de formulários
     *
     */
    private Integer centroHospitalar;
    private Integer pais;
    private Integer provincia;
    private Integer municipio;
    private Integer departamento;
    private Integer profissao;
    private Integer curso;

    public Integer getCentroHospitalar ()
    {
        return centroHospitalar;
    }

    public void setCentroHospitalar (Integer centroHospitalar)
    {
        this.centroHospitalar = centroHospitalar;
    }

    public Integer getPais ()
    {
        return pais;
    }

    public void setPais (Integer pais)
    {
        this.pais = pais;
    }

    public Integer getProvincia ()
    {
        return provincia;
    }

    public void setProvincia (Integer provincia)
    {
        this.provincia = provincia;
    }

    public Integer getMunicipio ()
    {
        return municipio;
    }

    public void setMunicipio (Integer municipio)
    {
        this.municipio = municipio;
    }

    public Integer getDepartamento ()
    {
        return departamento;
    }

    public void setDepartamento (Integer departamento)
    {
        this.departamento = departamento;
    }

    public Integer getProfissao ()
    {
        return profissao;
    }

    public void setProfissao (Integer profissao)
    {
        this.profissao = profissao;
    }

    public Integer getCurso ()
    {
        return curso;
    }

    public void setCurso (Integer curso)
    {
        this.curso = curso;
    }

}
