
package com.estudos.estagio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.estudos.estagio.service.ServiceAluno;
import com.estudos.estagio.service.ServiceEmpresa;
import com.estudos.estagio.service.ServiceEnsino;
import com.estudos.estagio.service.ServiceContratoEstagio;

import com.estudos.estagio.dto.dtoAluno.*;
import com.estudos.estagio.dto.dtoEmpresa.*;
import com.estudos.estagio.dto.dtoEnsino.*;
import com.estudos.estagio.dto.dtoContratoEstagio.*;

@Controller
@RequiredArgsConstructor
public class ControllerPage {

    private final ServiceAluno serviceAluno;
    private final ServiceEmpresa serviceEmpresa;
    private final ServiceEnsino serviceEnsino;
    private final ServiceContratoEstagio serviceContrato;

    @GetMapping("/")
    public String inicio(
            @RequestParam(required = false) String editar,
            @RequestParam(required = false) Long id,
            Model model) {

        boolean editarAluno = "aluno".equals(editar) && id != null;
        boolean editarEmpresa = "empresa".equals(editar) && id != null;
        boolean editarEnsino = "ensino".equals(editar) && id != null;
        boolean editarContrato = "contrato".equals(editar) && id != null;

        model.addAttribute("alunoFormulario",
                editarAluno ? serviceAluno.buscarParaEdicao(id)
                        : DadosCadastroAluno.vazio());

        model.addAttribute("empresaFormulario",
                editarEmpresa ? serviceEmpresa.buscarParaEdicao(id)
                        : DadosCadastroEmpresa.vazio());

        model.addAttribute("ensinoFormulario",
                editarEnsino ? serviceEnsino.buscarParaEdicao(id)
                        : DadosCadastroEnsino.vazio());

        model.addAttribute("contratoFormulario",
                editarContrato ? serviceContrato.buscarParaEdicao(id)
                        : DadosCadastroContratoEstagio.vazio());

        model.addAttribute("idAlunoEdicao", editarAluno ? id : null);
        model.addAttribute("idEmpresaEdicao", editarEmpresa ? id : null);
        model.addAttribute("idEnsinoEdicao", editarEnsino ? id : null);
        model.addAttribute("idContratoEdicao", editarContrato ? id : null);

        model.addAttribute("alunos", serviceAluno.listar(null));
        model.addAttribute("empresas", serviceEmpresa.listar(null));
        model.addAttribute("ensinos", serviceEnsino.listar(null));
        model.addAttribute("contratos", serviceContrato.listar());

        return "index";
    }

    @PostMapping("/pagina/ensinos")
    public String salvarEnsino(
            @RequestParam(required = false) Long id,
            @Valid @ModelAttribute DadosCadastroEnsino dados,
            BindingResult erros,
            RedirectAttributes atributos) {

        if (erros.hasErrors()) {
            return erroValidacao(erros, atributos);
        }

        return executar(atributos, () -> {
            if (id == null) {
                serviceEnsino.criar(dados);
            } else {
                serviceEnsino.atualizar(
                        new DadosAtualizacaoEnsino(id, dados.nome()));
            }
        });
    }

    @PostMapping("/pagina/alunos")
    public String salvarAluno(
            @RequestParam(required = false) Long id,
            @Valid @ModelAttribute DadosCadastroAluno dados,
            BindingResult erros,
            RedirectAttributes atributos) {

        if (erros.hasErrors()) {
            return erroValidacao(erros, atributos);
        }

        return executar(atributos, () -> {
            if (id == null) {
                serviceAluno.criar(dados);
            } else {
                serviceAluno.atualizar(
                        new DadosAtualizacaoAluno(
                                id,
                                dados.nomeCompleto(),
                                dados.telefone(),
                                dados.email(),
                                dados.curso(),
                                dados.semestre(),
                                dados.periodo(),
                                dados.idEnsino()));
            }
        });
    }

    @PostMapping("/pagina/empresas")
    public String salvarEmpresa(
            @RequestParam(required = false) Long id,
            @Valid @ModelAttribute DadosCadastroEmpresa dados,
            BindingResult erros,
            RedirectAttributes atributos) {

        if (erros.hasErrors()) {
            return erroValidacao(erros, atributos);
        }

        return executar(atributos, () -> {
            if (id == null) {
                serviceEmpresa.criar(dados);
            } else {
                serviceEmpresa.atualizar(
                        new DadosAtualizacaoEmpresa(
                                id,
                                dados.nomeEmpresa(),
                                dados.areaAtuacao(),
                                dados.cidade(),
                                dados.cep(),
                                dados.email(),
                                dados.site(),
                                dados.tel(),
                                dados.nomeContato(),
                                dados.cargoContato(),
                                dados.departamento(),
                                dados.linked()));
            }
        });
    }

    @PostMapping("/pagina/contratos")
    public String salvarContrato(
            @RequestParam(required = false) Long id,
            @Valid @ModelAttribute DadosCadastroContratoEstagio dados,
            BindingResult erros,
            RedirectAttributes atributos) {

        if (erros.hasErrors()) {
            return erroValidacao(erros, atributos);
        }

        return executar(atributos, () -> {
            if (id == null) {
                serviceContrato.criar(dados);
            } else {
                serviceContrato.atualizar(
                        new DadosAtualizacaoContratoEstagio(
                                id,
                                dados.idAluno(),
                                dados.idEmpresa(),
                                dados.dataInicio(),
                                dados.dataFim(),
                                dados.cargaHoraria(),
                                dados.tipoCargaHoraria(),
                                dados.areaEstagio(),
                                dados.atividadesPrincipais(),
                                dados.supervisorResponsavel(),
                                dados.cargoSupervisor()));
            }
        });
    }

    @PostMapping("/pagina/ensinos/{id}/excluir")
    public String excluirEnsino(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        return executar(atributos, () -> serviceEnsino.deletar(id));
    }

    @PostMapping("/pagina/alunos/{id}/excluir")
    public String excluirAluno(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        return executar(atributos, () -> serviceAluno.deletar(id));
    }

    @PostMapping("/pagina/empresas/{id}/excluir")
    public String excluirEmpresa(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        return executar(atributos, () -> serviceEmpresa.deletar(id));
    }

    @PostMapping("/pagina/contratos/{id}/excluir")
    public String excluirContrato(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        return executar(atributos, () -> serviceContrato.deletar(id));
    }

    private String erroValidacao(
            BindingResult erros,
            RedirectAttributes atributos) {

        atributos.addFlashAttribute(
                "erro",
                erros.getAllErrors().get(0).getDefaultMessage());

        return "redirect:/";
    }

    private String executar(
            RedirectAttributes atributos,
            Runnable operacao) {

        try {
            operacao.run();
            atributos.addFlashAttribute(
                    "sucesso",
                    "Operação realizada com sucesso.");
        } catch (RuntimeException erro) {
            atributos.addFlashAttribute(
                    "erro",
                    erro.getMessage());
        }

        return "redirect:/";
    }
}
