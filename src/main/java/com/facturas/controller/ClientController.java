package com.facturas.controller;

import com.facturas.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("lista",service.findAll());

        return "listar";

    }


}
