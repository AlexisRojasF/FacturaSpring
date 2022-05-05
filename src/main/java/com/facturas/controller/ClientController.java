package com.facturas.controller;

import com.facturas.models.entity.Client;
import com.facturas.services.ClientServiceImpl;
import com.facturas.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @RequestMapping(value = "/listar",method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page",defaultValue = "0") int page, Model model ){

        System.out.println(page);
        Pageable pageRq = PageRequest.of(page,4);

        Page<Client> clients = service.findAll(pageRq);

        PageRender<Client> pageRender = new PageRender<>("/listar",clients);

        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clients",clients);
        model.addAttribute("page",pageRender);

        return "listar";

    }

    @GetMapping("/form")
    public String crear(Map<String,Object> model){
        Client client = new Client();
        model.put("client",client);
        model.put("titulo","Formulario de Cliente");
        return "form";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable("id") Long id,Map<String,Object> model,RedirectAttributes flash){
        Client client = null;

        if (id > 0){
            client = service.findOne(id).orElse(null);
        }else{
            flash.addFlashAttribute("danger","Cliente No existe");
            return "redirect:listar";
        }
        model.put("client",client);
        model.put("titulo","Editar Cliente");
        return "form";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,RedirectAttributes flash){

        if (id > 0){
           service.delete(id);
           flash.addFlashAttribute("success","Cliente Eliminado");
        }
        return "redirect:/listar";


    }

    @PostMapping("/form")
    public String save(@Valid Client client, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario Cliente");
            return "form";
        }
        String mensaje = (client.getId() != null)? "Cliente Editado":"Cliente Creado";
        service.save(client);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:listar";
    }


}
