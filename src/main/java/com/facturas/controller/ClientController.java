package com.facturas.controller;

import com.facturas.models.entity.Client;
import com.facturas.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
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

    @PostMapping("/form")
    public String save(Client client, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario Cliente");
            return "form";
        }

        service.save(client);
        return "redirect:listar";
    }


}
