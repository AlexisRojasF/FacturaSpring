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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;


    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Long id,Map<String,Object> model,RedirectAttributes flash){
        Optional<Client> cliente = service.findOne(id);
        if (!cliente.isPresent()){
            flash.addFlashAttribute("error","el cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        model.put("client",cliente.get());
        model.put("titulo","Detalle del cliente: " +cliente.get().getName());

        return "ver";
    }

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
            Client client = service.findOne(id).get();
           service.delete(id);
           flash.addFlashAttribute("success","Cliente Eliminado");

           Path rootPath = Paths.get("uploads").resolve(client.getFoto()).toAbsolutePath();
            File archivo = rootPath.toFile();

            if(archivo.exists() && archivo.canRead()){
                if (archivo.delete()){
                    flash.addFlashAttribute("info", "img eliminada");
                }
            }
        }
        return "redirect:/listar";


    }

    @RequestMapping(value = "/form" ,method = RequestMethod.POST)
    public String save(@Valid Client client, BindingResult result, @RequestParam("file") MultipartFile foto, Model model, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario Cliente");
            return "form";
        }

        if (!foto.isEmpty()){
            Path diretorioRecursos= Paths.get("src//main//resources//static//img//uploads");
            String rootPath = diretorioRecursos.toFile().getAbsolutePath();
            try {
                byte[] bytes =foto.getBytes();
                Path retaCompleta = Paths.get(rootPath+"//"+foto.getOriginalFilename());
                Files.write(retaCompleta,bytes);
                flash.addFlashAttribute("info","Has subido Correctamente '"+
                        foto.getOriginalFilename()+"'");
                client.setFoto(foto.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String mensaje = (client.getId() != null)? "Cliente Editado":"Cliente Creado";
        service.save(client);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:listar";
    }


}
