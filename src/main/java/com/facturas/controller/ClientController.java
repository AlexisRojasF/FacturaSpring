package com.facturas.controller;

import com.facturas.models.entity.Client;
import com.facturas.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clients",service.findAll());

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
    public String editar(@PathVariable("id") Long id,Map<String,Object> model){
        Client client = null;

        if (id > 0){
            client = service.findOne(id).orElse(null);
        }else{
            return "redirect:listar";
        }
        model.put("client",client);
        model.put("titulo","Editar Cliente");
        return "form";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id){

        if (id > 0){
           service.delete(id);
        }
        return "redirect:/listar";


    }

    @PostMapping("/form")
    public String save(@Valid Client client, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario Cliente");
            return "form";
        }

        service.save(client);
        status.setComplete();
        return "redirect:listar";
    }


}
